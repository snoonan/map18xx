(ns map18xx.timetravel )

;; =============================================================================
;; Time Travel
;;
;; example state:
;; {:current-path [0 1], :moments [[:a :b'] [[:b :c''] :c'] :c :d]}
;; :b' is the current state.
;;
;; state is maintained in a nested list, with each new state added to the end of the current list.
;; If the current item is not at the end of a list, then it is wrapped in a list then appended to.
;; Undo will move the current-path back one step
;;


(def app-history (atom {}))

(defn add-history
  [{ :keys [current-path moments] :as history} new-state]
  (let [
        next-path (update current-path (-(count current-path) 1) inc)
        exist? (get-in moments next-path nil)
        ]
    (if exist?
      (-> history
          (update :current-path conj 1)
          (assoc-in (into [:moments] current-path) [(get-in moments current-path) new-state]))
      (-> history
          (assoc :current-path next-path)
          (update-in (into [:moments] (take (-(count current-path) 1) current-path)) conj new-state)))))

(defn prev-history
  "Address of previous item in history or nil if first"
  [{ :keys [current-path]}]
  (if (= 0 (last current-path))
    (loop [i current-path]
      (if (= 0 (count i))
        nil
        (if (= 0 (last i))
          (recur (pop i)) (update i (dec (count i)) dec))))
    (update current-path (-(count current-path) 1) dec)))

(defn next-history
  "Address of next item in history or nil if last"
  [{ :keys [current-path moments]}]
  (let [next-path (update current-path (dec (count current-path)) inc)
        ]
    (loop [i next-path]
      (if (vector? (get-in moments i))
          (recur (conj i 0))
          (if (get-in moments i) i nil)))))

(defn watch-state
  "Retain historical views of the state, but remove the top level tag
   :ephemeral to allow for non-historically interesting state to be
   held in the atom"
  [state]
  (do
    (remove-watch state :history)
    (reset! app-history {:current-path [0] :moments [@state]})
    (add-watch state :history
      (fn [_ _ _ n]
        (when-not (= (get-in (@app-history :moments) (@app-history :current-path) nil)
                     (select-keys n (filter #(not= "ephemeral" (namespace %)) (keys n))))
          (swap! app-history add-history
                 (select-keys n (filter #(not= "ephemeral" (namespace %)) (keys n)))))))))

(defn undo
  [app-state]
  (do
    (swap! app-history assoc :current-path (prev-history @app-history))
    (reset! app-state (get-in (get @app-history :moments) (get @app-history :current-path)))))

(defn redo
  [app-state]
  (do
    (swap! app-history assoc :current-path (next-history @app-history))
    (reset! app-state (get-in (get @app-history :moments) (get @app-history :current-path)))))

(defn goto
  [new-path app-state]
  (do
    (swap! app-history assoc :current-path new-path)
    (reset! app-state (get-in (get @app-history :moments) (get @app-history :current-path)))))

(defn ttg-helper
  "[[ &path ] &[moments] ] :as running
  running is a list (timelines) of lists (history)  of entries (moments) with the most recent first
  and decending at the branch point gets further back in time. Timelines can branch again, and the
  is nested and relative."
  [indentf itemf [path & worlds :as running] item]
   (if (vector? item)
     (let [
           sub-items (reduce (partial ttg-helper indentf itemf) [(conj path 0) []] item)
           merged (into (last worlds) (second sub-items))
           new-worlds (rest (rest sub-items))
           skip-list (mapv indentf (filter #(< 0 %) (update path 0 inc)))
           new-path (update path (dec (count path)) inc)
           ]
         (-> [new-path]
             (into (butlast worlds))
             (conj merged)
             (into new-worlds)
             (conj skip-list)))
     (-> running
         ;; step along current-path
         (update-in [0 (dec (count path))] inc)
         ;; insert moment
         (update-in [(count worlds)] conj (itemf path (= path (:current-path @app-history)))))))


;; {:current-path [0 1], :moments [[:a :g [:h :j :k] :i] [[:b :f] :e] :c [:d :l [:m :o [:p :r :s] :q :t] :n] :o]}
;; should render as:
;;  a g h j k             ; 0     --
;;  _ ___ i               ; 03    s1 s2
;;  _ b f                 ; 10    s1
;;  ___ e                 ; 11    s2
;;  ___ c d l m o p r s   ; 2     s2
;;  _______ ___ ___ q t   ; 323   s4 s2 s2
;;  _______ ___ n         ; 33    s4 s2
;;  _______ o             ; 4     s4
;;
;; [[0] []] -> reduce [[0 0] []] [:a :g [h j k] i]
;;          [[0 0] []] -> :a
;;          [[0 1] [i00] -> :g
;;          [[0 2] [i00 i01]] -> reduce [[0 2 0] []] [:h :j :k]
;;                  [[0 2 0] [] -> :h
;;                  [[0 2 1] [020] -> :j
;;                  [[0 2 2] [020 021] -> :k
;;                  [[0 2 3] [i020 i021 i022] <-
;;          [[0 3] [i00 i01 i020 i021 i022] [s1 s2]]  ; 1 = 0++   2 = 3--
;;          [[0 3] [i00 i01 i020 i021 i022] [s1 s2]] -> :i
;;          [[0 4] [i00 i01 i020 i021 i022] [s1 s2 i03]] <-
;; [[1] [i00 i01 i020 i021 i022] [s1 s2 i03] [s1]] -> reduce [[1 0] []] [[:b :e] :e]
;;          [[1 0] [] -> reduce [[1 0 0] []] [b e]
;;                  [1 0 0] [] -> b
;;                  [1 0 1] [i100] -> f
;;                  [1 0 2] [i100 i101] <-
;;          [[1 1] [i100 i101] [s2]  ; 2 = 1++  0 = 1--
;;          [[1 1] [i100 i101] [s2] -> e
;;          [[1 2] [i100 101] [s2 i11] <-
;; [[2] [i00 i01 i020 i021 i022] [s1 s2 i03] [s1 i100 101] [s2 i11] [s2]]  ; 2 = 2++--
;; [[2] [i00 i01 i020 i021 i022] [s1 s2 i03] [s1 i100 101] [s2 i11] [s2]] -> :c
;; [[3] [i00 i01 i020 i021 i022] [s1 s2 i03] [s1 i100 101] [s2 i11] [s2]] -> reduce [[3 0] [] [:d :l [:m :o [:p :r :s] :q :t] :n]
;;          [[3 0] []] -> d
;;          [[3 1] [i30]] -> l
;;          [[3 2] [i30 i31] -> reduce [[3 2 0] [] [m o [p r s] q t]
;;                  [[3 2 0] []] -> m
;;                  [[3 2 1] [i320] -> o
;;                  [[3 2 2] [i320 i321] -> reduce [3 2 2 0] [] [p r s]
;;                        [3 2 2 0] [] -> p
;;                        [3 2 2 1] [i3220] -> r
;;                        [3 2 2 2] [i3220 i3221] -> s
;;                        [3 2 2 2] [i3220 i3221 i3222] <-
;;                  [[3 2 3] [i320 i321 3220 3221 3222] [s4 s2 s2]  ; 4=3++ 2=2  2=3--
;;                  [[3 2 3] [i320 i321 3220 3221 3222] [s4 s2 s2]  -> q
;;                  [[3 2 4] [i320 i321 3220 3221 3222] [s4 s2 s2 i323]  -> t
;;                  [[3 2 5] [i320 i321 3220 3221 3222] [s4 s2 s2 i323 i324]  <-
;;          [[3 3] [i30 i31 i320 i321 3220 3221 3222] [s4 s2 s2 i323 i324] [s4 s2] ; 3++ 3--
;;          [[3 3] [i30 i31 i320 i321 3220 3221 3222] [s4 s2 s2 i323 i324] [s4 s2] -> n
;;          [[3 3] [i30 i31 i320 i321 3220 3221 3222] [s4 s2 s2 i323 i324] [s4 s2 i33] <-
;; [[4] [...] [..] [..] [..] [..] [s2 s2 i33] [s4]] ; 4++--
;; [[4] [...] [..] [..] [..] [..] [..] [s4]] -> :o
;; [[5] [...] [..] [..] [..] [..] [..] [s4 i4] <-

(defn transform-to-grid
"Transform the history into a horizantal grid display
use as: (transform-to-grid  #(do [:skip %]) #(do [:item %]) @app-history)"
   [indentf itemf {:keys [moments]}]
   (rest (reduce (partial ttg-helper indentf itemf) [[0] []] moments)))
