(ns map18xx.tiles
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljsjs.react]
            [map18xx.upg :as upg]
            [map18xx.map1820 :as board]
            [map18xx.utils :as utils]
            ))

(def draw-state (atom {}))

(declare draw-tile)

; Because react.dom does not have an entry for 'use' so create one here.
(def dom-use (js/React.createFactory "use"))

(defn intersect-track
  [tile edges upgrademaps]
  (if-not (nil? upgrademaps)
  (let [ [tiles upgrades] (first upgrademaps)
         tile-edges (tiles tile) ]
    (if-not (nil? tile-edges)
      (upgrades (into tile-edges edges))
      (recur tile edges (next upgrademaps))))))

(defn upgrade-to
  "Determine tile and orient to upgrade a tile to when adding a path from e1 to e2."
  [{ :keys [pos tile orient]} edges]
  (let [ real (vec (filter #(not (nil? %)) edges))
        [neworient newtile] (upg/unique-path? tile orient real) ]
      (if (nil? newtile)
        nil
        {:pos pos :tile newtile :orient neworient})))

(defn insert-tile-direct
  "Add a path to a tile."
  [[this pos-entry] e1 e2]
  (if (not (= e1 e2))
      (if-let [upgrade (upgrade-to pos-entry [e1 e2])]
        (do
          (om/transact! this `[(hex/lay-tile ~upgrade)])
          true)
      false)))

(defn insert-tile
  "Add a path to a tile."
  [[this pos-entry] e1 e2]
  (insert-tile-direct [this pos-entry] e1 (mod (+ 3 e2) 6)))

(defn hex-down
  [pos-entry pos this]
  (swap! draw-state (fn [] {:in [nil pos]  :last [this pos-entry] :drawing true}))
  nil)

(defn hex-up
  []
  (swap! draw-state assoc-in [:drawing] false)
  nil)


(defn hex-enter
  [pos-entry pos this]
    (if (:drawing @draw-state)
      (let [lastpos ((:in @draw-state) 1)
               direction (utils/find-direction lastpos pos (:rotate board/app-state))
               newdraw {:in [((:in @draw-state) 1) pos] :from direction :last [this pos-entry] :drawing true}
               ]
           (do
             (or
               (insert-tile (:last @draw-state) (:from @draw-state) (:from newdraw))
               (insert-tile (:last @draw-state) nil (:from newdraw)))   ; just the exit leg matters. Do this AFTER testing for both legs.
             (insert-tile (:last newdraw) nil (+ 3 (:from newdraw)))    ; No need to wrap as it will be wrapped later.
             (swap! draw-state (fn [] newdraw))))
      (swap! draw-state assoc-in [:last] [this pos-entry]))
    nil)


(defn hex-edit
  [props pos this]
     (om/transact! (om/get-reconciler this) `[(draw/edit-edge ~{:this this :props props :in []}) :ephemeral]))

(defn hex-edge
  [ this to-edit props in pos ]
      (let [
            in' (filter #(not (= pos %)) in)
            in (if (= in in') (conj (if (keyword pos) (filter #(not (keyword? %)) in) in) pos) in')
            check-in (filter #(not (keyword? %)) in)
            new-tile (some #(if (keyword? %) % false) in)
            tile (:tile props)
            tile (if new-tile (name new-tile) tile)
            new-props (assoc props :tile tile)
            insert (apply insert-tile-direct [to-edit new-props] check-in)    ; No need to wrap as it will be wrapped later.
            ]
        (if insert
         (om/transact! this '[(draw/edit-done)])
         (om/transact! this `[(draw/edit-edge ~{:this to-edit :props props :in in})]))))

(defui TileView
    static om/Ident
    (ident [this {:keys [pos]}] [:tile/by-pos pos])
    static om/IQuery
    (query [this] '[:pos :tile :orient :overlay :label :color])
    Object
    (render [this]
    (let [{:keys [tile pos orient] :as props} (om/props this)
          [row col] (utils/pos-to-rc pos)
          rotate  (:rotate board/app-state)
          width (if (= rotate 30) 0.86 1.5)
          height (if (= rotate 30) 1.5 0.86)
          ]
      (apply dom/g #js { :transform (str "translate("
                              (+ 4 (* width col)) ","
                              (+ 4 (* height row)) ")"
                              "rotate(" rotate ")")
                   :onMouseDown
                      (fn [e] (hex-down props pos this))
                   :onMouseEnter
                      (fn [e] (hex-enter props pos this))
                   :onMouseUp
                      (fn [e] (hex-up))
                   :onClick
                      (fn [e] (hex-edit props pos this))
                  }
        (-> '()
        (into (if (contains? props :overlay)
          (map (fn [[tile orient]] (let [rotate (if orient orient -0.5) ]
                 (dom-use #js {:xlinkHref (str "defs.svg#" tile)
                               :transform (str "rotate(" (* rotate 60) ")")}))) (:overlay props)) '()))
        (into (if (contains? props :label)
          (list (dom/text #js {:x (get-in props [:label 0 0])
                         :y (get-in props [:label 0 1])
                         :fontSize 16 :textAnchor "middle"
                         :fill "white"
                         :transform (str "scale(0.02) rotate(-" rotate ")")} (get-in props [:label 1]))) '()))
        (conj 
                (draw-tile tile orient (:color props))))))))

(def tile-view (om/factory TileView {:keyfn :pos}))

(defui TileEditView
    static om/Ident
    (ident [this id]  [:ephemeral :draw])
    static om/IQuery
    (query [this] '[:in :drawing :last])
    Object
    (render [this]
    (let [
          {:keys [in last drawing]} (om/props this)
          [to-edit {:keys [pos tile] :as props}] last
          [row col] (utils/pos-to-rc pos)
          rotate  (:rotate board/app-state)
          width (if (= rotate 30) 0.86 1.5)
          height (if (= rotate 30) 1.5 0.86)
          ]
       (if drawing
         (dom/g #js { :transform (str "translate(" (+ 4 (* width col)) "," (+ 4 (* height row)) ") "
                              "rotate(" rotate ") scale(1.5)")
                      :id "upgrade-select" }
             (-> [(dom-use #js {:xlinkHref (str "defs.svg#hex")
                                  :transform (str "rotate(0)")
                                  :fill "blue" :opacity "0.1"})]
                 (into (if (= "t500" tile)
                        [(dom-use #js {:xlinkHref (str "defs.svg#target")
                                       :transform (str "translate(0.25,0)")
                                       :color (if (= in (filter #(not (= :t503 %)) in)) "white" "green")
                                       :onClick (fn [e] (hex-edge this to-edit props in :t503))})
                         (dom-use #js {:xlinkHref (str "defs.svg#btarget")
                                       :transform (str "translate(-0.125,-0.216)")
                                       :color (if (= in (filter #(not (= :tbig %)) in)) "white" "green")
                                       :onClick (fn [e] (hex-edge this to-edit props in :tbig))})
                         (dom-use #js {:xlinkHref (str "defs.svg#dit")
                                       :transform (str "translate(-0.125,0.216)")
                                       :color (if (= in (filter #(not (= :t501 %)) in)) "black" "green")
                                       :onClick (fn [e] (hex-edge this to-edit props in :t501))})]
                         []))
                 (into (for [orient [0 1 2 3 4 5]]
                   (dom-use #js {:xlinkHref (str "defs.svg#target")
                                 :transform (str "rotate(" (* 60  (+ 3 orient)) ") translate(0,0.866)")
                                 :color (if (= in (filter #(not (= orient %)) in)) "white" "green")
                                 :onClick (fn [e] (hex-edge this to-edit props in orient))})))))))))

(def tile-edit-view (om/factory TileEditView {:keyfn :drawing}))

;; negative colors are spaced by two so no upgrades are possible
(def hex-colors {0 "white"
                 1 "yellow"
                 2 "green"
                 3 "brown"
                 4 "lightgrey"
                 -2 "grey"
                 -4 "red"
                 -6 "blue"})

;; points in a hex with a unit side centered around the origin
(def hex-points {0   [ 0     -0.866]
                 1   [ 0.750 -0.443]
                 2   [ 0.750  0.443]
                 3   [ 0      0.866]
                 4   [-0.750  0.443]
                 5   [-0.750 -0.443]
                 "A" [ 0      0]
                 "B" [ 0     -0.216]
                 "C" [ 0.125 -0.216]
                 "D" [ 0.188 -0.108]
                 "E" [ 0.250  0]
                 "F" [ 0.188  0.108]
                 "G" [ 0.125  0.216]
                 "H" [ 0      0.216]
                 "I" [-0.125  0.216]
                 "J" [-0.188  0.108]
                 "K" [-0.250  0]
                 "L" [-0.188 -0.108]
                 "M" [-0.125 -0.216]
                 "N" [ 0     -0.577]
                 "Z" [ 0.250 -0.443]
                 "P" [ 0.500 -0.289]
                 "Q" [ 0.500  0]
                 "R" [ 0.500  0.289]
                 "S" [ 0.250  0.443]
                 "T" [ 0      0.577]
                 "U" [-0.250  0.443]
                 "V" [-0.500  0.289]
                 "W" [-0.500  0]
                 "X" [-0.500 -0.289]
                 "Y" [-0.250 -0.443]
                 })

(defn draw-tile
  [tile orient color]
  (let [tile-info (board/track-list tile)
        _ (prn tile color)
        color (or color (hex-colors (get tile-info "p.")))
        _ (prn tile-info color)
        ]
(dom/g #js { :transform (str "rotate(" (* orient 60) ")") }
    (reduce
          (fn [a [[op location] v]]
            (let [location-real (or (tile-info (str "a" location)) location)
                  stations (or (tile-info (str "r" location)) location-real)
                  _ (prn op location location-real stations tile-info)
                  ]
             (-> a
              (into (if (sequential? v)
                      (for [track-set (if (sequential? (first v)) v (if (= 2 (count v)) [v] (map (fn [x] [location-real x]) v)))]
                        (let [[e1 e2] track-set]
                          (if-not (and (number? e1) (number? e2))
                            (dom/path #js {:d (str "M" ((get hex-points e1) 0) " "
                                                       ((get hex-points e1) 1)
                                                   "L" ((get hex-points e2) 0) " "
                                                       ((get hex-points e2) 1))
                                           :stroke "black" :strokeWidth 0.1})
                            (case (- e2 e1)
                              1 (dom-use #js {:xlinkHref (str "defs.svg#sharp")
                                           :transform (str "rotate(" (* e1 60) ")")})
                              2 (dom-use #js {:xlinkHref (str "defs.svg#gentle")
                                           :transform (str "rotate(" (* e1 60) ")")})
                              3 (dom-use #js {:xlinkHref (str "defs.svg#straight")
                                           :transform (str "rotate(" (* e1 60) ")")})
                              4 (dom-use #js {:xlinkHref (str "defs.svg#gentle")
                                           :transform (str "rotate(" (* e2 60) ")")})
                              5 (dom-use #js {:xlinkHref (str "defs.svg#sharp")
                                           :transform (str "rotate(" (* e2 60) ")")}))))
                                       ) [] ))
              (into (case op
                "c" (into
                      (for [loc stations]
                       (dom-use #js {:xlinkHref (str "defs.svg#city")
                                     :transform (str "translate(" ((get hex-points loc) 0)","((get hex-points loc) 1) ")")}))
                      (for [loc1 stations loc2 stations]
                       (dom/path #js {:d (str "M" ((get hex-points loc1) 0) " "
                                                  ((get hex-points loc1) 1) " "
                                             "L " ((get hex-points loc2) 0) " "
                                                  ((get hex-points loc2) 1))
                                      :stroke "white"
                                      :strokeWidth 0.5})))
                "d" (for [loc stations]
                       (dom-use #js {:xlinkHref (str "defs.svg#dit")
                                     :transform (str "translate(" ((get hex-points loc) 0)","((get hex-points loc) 1) ")")}))
                "l" [(dom/text #js {:x (* 50 ((get hex-points location) 0)) :y (* 50 ((get hex-points location) 1))
                                    :fontSize 16 :textAnchor "middle"
                                    :fill "black"
                                    :transform "scale(.02)"} v)]
                nil)))))
            [ (dom-use #js {:xlinkHref (str "defs.svg#hex") :fill color}) ] tile-info))))

