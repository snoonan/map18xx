(ns map18xx.core
  (:require [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [clojure.string :as string]))

(enable-console-print!)

(def upgrade-map {
                  [0 1 0 2] ["t29" 0]
                  [0 1 0 3] ["t27" 0]
                  [0 1 0 4] ["t30" 0]
                  [0 1 0 5] ["t624" 0]
                  [0 1 1 2] ["t624" 1]
                  [0 1 1 3] ["t31" 1]
                  [0 1 1 4] ["t26" 1]
                  [0 1 1 5] ["t28" 5]
                  [0 1 2 3] ["t625" 0]
                  [0 1 2 4] ["t22" 2]
                  [0 1 2 5] ["t18" 5]
                  [0 1 3 4] ["t626" 0]
                  [0 1 3 5] ["t21" 3]
                  [0 1 4 5] ["t625" 4]
                  [0 2 0 1] ["t29" 0]
                  [0 2 0 3] ["t24" 0]
                  [0 2 0 4] ["t25" 0]
                  [0 2 0 5] ["t31" 0]
                  [0 2 1 2] ["t28" 0]
                  [0 2 1 3] ["t16" 5]
                  [0 2 1 4] ["t19" 1]
                  [0 2 1 5] ["t16" 0]
                  [0 2 2 3] ["t30" 2]
                  [0 2 2 4] ["t25" 2]
                  [0 2 2 5] ["t23" 2]
                  [0 2 3 4] ["t21" 3]
                  [0 2 3 5] ["t17" 0]
                  [0 2 4 5] ["t22" 0]
                  [0 3 0 1] ["t27" 0]
                  [0 3 0 2] ["t24" 0]
                  [0 3 0 4] ["t23" 0]
                  [0 3 0 5] ["t26" 0]
                  [0 3 1 2] ["t18" 0]
                  [0 3 1 3] ["t23" 3]
                  [0 3 1 4] ["t20" 0]
                  [0 3 1 5] ["t19" 0]
                  [0 3 2 3] ["t26" 3]
                  [0 3 2 4] ["t19" 3]
                  [0 3 2 5] ["t20" 5]
                  [0 3 3 4] ["t26" 3]
                  [0 3 3 5] ["t24" 3]
                  [0 3 4 5] ["t18" 3]
                  })

(def track-map {
                "t7"  [0 1]
                "t8"  [0 2]
                "t9"  [0 3]
                "t16" [0 2 1 3]
                "t17" [0 2 3 5]
                "t18" [0 3 1 1]
                "t19" [0 3 1 5]
                "t20" [0 3 1 4]
                "t21" [0 2 3 4]
                "t22" [0 3 4 5]
                "t23" [0 2 0 3]
                "t24" [0 2 0 4]
                "t25" [0 2 0 4]
                "t26" [0 3 0 5]
                "t27" [0 1 0 3]
                "t28" [0 2 1 2]
                "t29" [0 1 0 2]
                "t30" [0 1 0 4]
                "t31" [0 2 0 5]
                "t624" [0 1 0 5]
                "t625" [0 1 2 3]
                "t626" [0 1 3 4]
                })

(def app-state {
    :rotate 30
    :scale 20
    :tiles [
            (comment tile map
            { :pos "a1" :tile "t7"    :orient 0 }
            { :pos "a3" :tile "t8"    :orient 0 }
            { :pos "a5" :tile "t9"    :orient 0 }
            { :pos "c1" :tile "t14"    :orient 0 }
            { :pos "c3" :tile "t15"    :orient 0 }
            { :pos "c5" :tile "t16"    :orient 0 }
            { :pos "c7" :tile "t17"    :orient 0 }
            { :pos "c9" :tile "t18"    :orient 0 }
            { :pos "e1" :tile "t19"    :orient 0 }
            { :pos "e3" :tile "t20"    :orient 0 }
            { :pos "e5" :tile "t21"    :orient 0 }
            { :pos "e7" :tile "t22"    :orient 0 }
            { :pos "e9" :tile "t23"    :orient 0 }
            { :pos "g1" :tile "t24"    :orient 0 }
            { :pos "g3" :tile "t25"    :orient 0 }
            { :pos "g5" :tile "t26"    :orient 0 }
            { :pos "g7" :tile "t27"    :orient 0 }
            { :pos "g9" :tile "t28"    :orient 0 }
            { :pos "i1" :tile "t29"    :orient 0 }
            { :pos "i3" :tile "t30"    :orient 0 }
            { :pos "i5" :tile "t31"    :orient 0 }
            { :pos "i7" :tile "t52"    :orient 0 }
            { :pos "i9" :tile "t624"    :orient 0 }
            { :pos "k1" :tile "t625"    :orient 0 }
            { :pos "k3" :tile "t626"    :orient 0 }
            )
            { :pos "a17" :tile "t7"   :orient 2 }
            { :pos "a19" :tile "t7"   :orient 2 }
            { :pos "b10" :tile "t503" :orient 0 }
            { :pos "b12" :tile "t500" :orient 0 }
            { :pos "b14" :tile "t500" :orient 0 }
            { :pos "b16" :tile "t503" :orient 0 }
            { :pos "b18" :tile "t500" :orient 0 }
            { :pos "b20" :tile "t500" :orient 0 }
            { :pos "b22" :tile "t500" :orient 0 }
            { :pos "c7"  :tile "t500" :orient 0 }
            { :pos "c9"  :tile "t500" :orient 0 }
            { :pos "c11" :tile "t500" :orient 0 }
            { :pos "c13" :tile "t500" :orient 0 }
            { :pos "c15" :tile "t500" :orient 0 }
            { :pos "c17" :tile "t500" :orient 0 }
            { :pos "c19" :tile "t500" :orient 0 }
            { :pos "c21" :tile "t500" :orient 0 }
            { :pos "c23" :tile "t500" :orient 0 }
            { :pos "d2"  :tile "t500" :orient 0 }
            { :pos "d4"  :tile "t500" :orient 0 }
            { :pos "d6"  :tile "t500" :orient 0 }
            { :pos "d8"  :tile "t500" :orient 0 }
            { :pos "d10" :tile "t500" :orient 0 }
            { :pos "d12" :tile "t500" :orient 0 }
            { :pos "d14" :tile "t500" :orient 0 }
            { :pos "d16" :tile "t500" :orient 0 }
            { :pos "d18" :tile "t500" :orient 0 }
            { :pos "d20" :tile "t500" :orient 0 }
            { :pos "d22" :tile "t500" :orient 0 }
            { :pos "d24" :tile "t500" :orient 0 }
            { :pos "e3"  :tile "t500" :orient 0 }
            { :pos "e5"  :tile "t500" :orient 0 }
            { :pos "e7"  :tile "t500" :orient 0 }
            { :pos "e9"  :tile "t500" :orient 0 }
            { :pos "e11" :tile "t500" :orient 0 }
            { :pos "e13" :tile "t500" :orient 0 }
            { :pos "e15" :tile "t500" :orient 0 }
            { :pos "e17" :tile "t500" :orient 0 }
            { :pos "e19" :tile "t503" :orient 0 }
            { :pos "e21" :tile "t500" :orient 0 }
            { :pos "e23" :tile "t500" :orient 0 }
            { :pos "f4"  :tile "t503" :orient 0 }
            { :pos "f6"  :tile "t500" :orient 0 }
            { :pos "f8"  :tile "t500" :orient 0 }
            { :pos "f10" :tile "t500" :orient 0 }
            { :pos "f12" :tile "t500" :orient 0 }
            { :pos "f14" :tile "t500" :orient 0 }
            { :pos "f16" :tile "t503" :orient 0 }
            { :pos "f18" :tile "t500" :orient 0 }
            { :pos "f20" :tile "t500" :orient 0 }
            { :pos "f22" :tile "t503" :orient 0 }
            { :pos "f24" :tile "t500" :orient 0 }
            { :pos "g3"  :tile "t500" :orient 0 }
            { :pos "g5"  :tile "t500" :orient 0 }
            { :pos "g7"  :tile "t500" :orient 0 }
            { :pos "g9"  :tile "t500" :orient 0 }
            { :pos "g11" :tile "t500" :orient 0 }
            { :pos "g13" :tile "t500" :orient 0 }
            { :pos "g15" :tile "t500" :orient 0 }
            { :pos "g17" :tile "t500" :orient 0 }
            { :pos "g19" :tile "t500" :orient 0 }
            { :pos "h2"  :tile "t500" :orient 0 }
            { :pos "h4"  :tile "t503" :orient 0 }
            { :pos "h6"  :tile "t500" :orient 0 }
            { :pos "h8"  :tile "t500" :orient 0 }
            { :pos "h10" :tile "t503" :orient 0 }
            { :pos "h12" :tile "t500" :orient 0 }
            { :pos "h14" :tile "t500" :orient 0 }
            { :pos "h16" :tile "t503" :orient 0 }
            { :pos "h18" :tile "t500" :orient 0 }
            { :pos "h20" :tile "t500" :orient 0 }
            { :pos "i3"  :tile "t500" :orient 0 }
            { :pos "i5"  :tile "t500" :orient 0 }
            { :pos "i7"  :tile "t500" :orient 0 }
            { :pos "i9"  :tile "t500" :orient 0 }
            { :pos "i11" :tile "t500" :orient 0 }
            { :pos "i13" :tile "t500" :orient 0 }
            { :pos "i15" :tile "t500" :orient 0 }
            { :pos "i17" :tile "t500" :orient 0 }
            { :pos "i19" :tile "t500" :orient 0 }
            { :pos "j4"  :tile "t500" :orient 0 }
            { :pos "j6"  :tile "t500" :orient 0 }
            { :pos "j8"  :tile "t500" :orient 0 }
            { :pos "j10" :tile "t500" :orient 0 }
            { :pos "j12" :tile "t500" :orient 0 }
            { :pos "j14" :tile "t503" :orient 0 }
            ]})

(defn pos-to-rc
 "Translate L## into (x y)"
 [pos]
 (if-let [[row & col] pos]
 [
  (- (.charCodeAt row 0) 97)
  (reduce #(+ (* %1 10) (int %2)) col) ]
 [-2 -2]))

(defui TileView
    static om/Ident
    (ident [this {:keys [pos]}] [:tile/by-pos pos])
    static om/IQuery
    (query [this] '[:pos :tile :orient])
    Object
    (render [this]
    (let [{:keys [tile pos orient] :as props} (om/props this)
          [row col] (pos-to-rc pos)
          rotate  (:rotate app-state)
          scale   (:scale app-state)
          width (if (= rotate 30) (* 0.86 scale) (* 1.5 scale))
          height (if (= rotate 30) (* 1.5 scale) (* 0.86 scale))
          ]
      (dom/g #js { :transform (str "translate("
                              (+ 40 (* width col)) ","
                              (+ 40 (* height row)) ")"
                              "rotate(" rotate ")")
                   :onMouseDown
                      (fn [e] (om/transact! this `[(hex/down {:pos ~pos})]))
                   :onMouseEnter
                      (fn [e] (om/transact! this `[(hex/enter {:pos ~pos :this ~this})]))
                   :onMouseUp
                      (fn [e] (om/transact! this `[(hex/up)]))
                  }
        (dom/use #js {:xlinkHref (str "defs.svg#" tile)
                  :transform (str "scale("scale") rotate(" (* orient 60) ")")}
                  (str orient ", points: " pos))))))

(def tile-view (om/factory TileView {:keyfn :pos}))

(defui MapView
       static om/IQuery
       (query [this]
              [{:tiles (om/get-query TileView)}])
       Object
       (render [this]
               (let [tiles (-> this om/props :tiles)]
                 (dom/g nil
                   (mapv tile-view tiles)))))


(defmulti read om/dispatch)
(defmethod read :tiles
    [{:keys [state] :as env} key params]
      { :value (into [] (map #(get-in @state %) (get @state key))) })

(defmulti mutate om/dispatch)

(defmethod mutate 'hex/down
  [{:keys [state]} _ {:keys [pos]}]
  {:action
   (fn []
     (swap! state assoc-in
       [:draw]
       {:in [nil pos] :drawing true}))
    :value {:keys [:draw]}})

(defmethod mutate 'hex/up
  [{:keys [state]} _ _]
  {:action
   (fn []
     (swap! state assoc-in
       [:draw]
       {:drawing false}))
    :value {:keys [:draw]}})

(defn find-direction
  "Find the edge number in the target hex when coming from the 'from' hex"
 [from to rotate]
 (let [p1 (pos-to-rc from)
       p2 (pos-to-rc to)
       dr (- (p2 0) (p1 0))
       dc (- (p2 1) (p1 1))
       ]
    (cond
      (and (= rotate  0) (= dr -2) (= dc  0)) 3
      (and (= rotate  0) (= dr -1) (= dc  1)) 4
      (and (= rotate  0) (= dr  1) (= dc  1)) 5
      (and (= rotate  0) (= dr  2) (= dc  0)) 0
      (and (= rotate  0) (= dr  1) (= dc -1)) 1
      (and (= rotate  0) (= dr -1) (= dc -1)) 2
      (and (= rotate 30) (= dr -1) (= dc  1)) 3
      (and (= rotate 30) (= dr  0) (= dc  2)) 4
      (and (= rotate 30) (= dr  1) (= dc  1)) 5
      (and (= rotate 30) (= dr  1) (= dc -1)) 0
      (and (= rotate 30) (= dr  0) (= dc -2)) 1
      (and (= rotate 30) (= dr -1) (= dc -1)) 2
    :else nil
    )))

(defn intersect-track
  [tile e1 _e2 ]
  (let [e2 (mod (+ 3 _e2) 6) _ (println (track-map tile) e1 e2)] ; 3+ to translate from second enter to exit edge
  (upgrade-map (into (track-map tile) (if (< e1 e2) [e1 e2] [e2 e1])))))

(defn upgrade-to
  "Determine tile and orient to upgrade a tile to when adding a path from e1 to e2."
  [{ :keys [pos tile orient]} e1 e2]
  (let [delta (- e2 e1)
        upgrade {"t500" ["t7" "t8" "t9"]    ;simple [sharp broad straight]
                 "t501" ["t3" "t58" "t4"]   ;town
                 "t503" ["t5" "t6" "t57"]   ;city
                         }
        [re1 re2] [(mod (- e1 orient) 6) (mod (- e2 orient) 6)]
        upg-set (get upgrade tile {})
        _ (println pos tile orient e1 e2 re1 re2)
        ]
    (if (vector? upg-set)
    (condp = delta
       1 { :pos pos :tile (upg-set 1) :orient (mod (+ 3 e2) 6) }
      -5 { :pos pos :tile (upg-set 1) :orient (mod (+ 3 e2) 6) }
      -1 { :pos pos :tile (upg-set 1) :orient e1 }
       5 { :pos pos :tile (upg-set 1) :orient e1 }
       2 { :pos pos :tile (upg-set 0) :orient (mod (+ 3 e2) 6) }
      -4 { :pos pos :tile (upg-set 0) :orient (mod (+ 3 e2) 6) }
      -2 { :pos pos :tile (upg-set 0) :orient e1 }
       4 { :pos pos :tile (upg-set 0) :orient e1 }
       0 { :pos pos :tile (upg-set 2) :orient e2 }
       :default nil)
    (let [[newtile neworient] (or (intersect-track tile re1 re2) [tile 0])]
      {:pos pos :tile newtile :orient (mod (+ orient neworient) 6)})
     )))

(defn insert-tile
  "Add a path to a tile."
  [this pos-entry e1 e2]
  (if (not (or (nil? e1) (nil? e2) (= e1 (mod (+ 3 e2) 6))))
    (om/transact! this `[(hex/lay-tile ~(upgrade-to pos-entry e1 e2))])))

(defmethod mutate 'hex/lay-tile
  [{:keys [state] :as env} _ {:keys [pos tile orient]}]
    (if (not (nil? tile))
      {:action
       (fn [] (swap! state assoc-in [:tile/by-pos pos] {:pos pos :tile tile :orient orient}))}))

(defmethod mutate 'hex/enter
  [{:keys [state] :as env} _ {:keys [pos this]}]
   (let [draw (:draw @state) ]
     (if (:drawing draw)
      {:action
       (fn []
         (let [lastpos ((:in draw) 1)
               direction (find-direction lastpos pos (:rotate @state))
               newdraw {:in [((:in draw) 1) pos] :from direction :last this :drawing true}
               ]
           (do
             (insert-tile (:last draw) (get-in @state [:tile/by-pos lastpos]) (:from draw) (:from newdraw))
             (swap! state assoc-in [:draw] newdraw)
           )))
       :value {:keys [:draw]}})))

(def reconciler
  (om/reconciler
    {:state app-state
     :parser (om/parser {:read read :mutate mutate})}))

(om/add-root! reconciler
  MapView (gdom/getElement "map"))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

