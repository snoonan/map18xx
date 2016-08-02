(ns map18xx.timetravel )

;; =============================================================================
;; Time Travel

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
  [state]
  (do
    (add-watch state :history
      (fn [_ _ _ n]
        (when-not (= (get-in (@app-history :moments) (@app-history :current-path) nil) n)
          (swap! app-history add-history n)
          )))
    (reset! app-history {:current-path [0] :moments [@state]})))

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
  [indentf itemf running item]
   (if (vector? item)
     (let [running' (reduce (partial ttg-helper indentf itemf)
                                  [(conj (running 0) 0) (running (-(count running) 1))] item)
           running'' (update running' 0 pop)
           running''' (update-in running'' [0 (dec (count (running 0)))] inc)
           ]
         (conj running''' (vec (map-indexed (partial indentf (running''' 0)) (running''' 0)))))
     (-> running
         (update-in [0 (dec (count (running 0)))] inc)
         (update-in [(dec (count running))] conj (itemf (running 0) (= (running 0) (:current-path @app-history)))))))

(defn transform-to-grid
"Transform the history into a horizantal grid display
use as: (transform-to-grid  #(do [:skip %]) #(do [:item %]) @app-history)"
   [indentf itemf {:keys [moments]}]
   (rest (reduce (partial ttg-helper indentf itemf) [[0] []] moments)))
