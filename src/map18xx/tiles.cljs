(ns map18xx.tiles
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljsjs.react]
            [clojure.string :as string]
            [map18xx.upg :as upg]
            [map18xx.utils :as utils]
            ))

(def draw-state (atom {}))

(declare draw-tile)

(defn lay-tile
  [state {:keys [pos tile] :as new-props}]
  (let [remaining (get-in state [:tileinv/by-tile tile :remaining])]
  (if (or (not= 0 remaining) (nil? remaining))
    (-> state
        (update-in [:tile/by-pos pos] merge new-props)
        (update-in [:tileinv/by-tile tile :remaining] #(if (nil? %) % (dec %)))
        (update-in [:tileinv/by-tile (get-in state [:tile/by-pos pos :tile]) :remaining] #(if (nil? %) nil (inc %)))
        ))))

;; negative colors are spaced by two so no upgrades are possible
(def hex-colors {0 "white"
                 1 "yellow"
                 2 "green"
                 3 "brown"
                 4 "lightgrey"
                 -2 "grey"
                 -4 "red"
                 -6 "blue"})

(defn- pos-in-hex
  [pos]
  (let [[r c] (utils/pos-to-rc pos) ]
    [(- (* .125  c) 1.125)
     (- (* .2165 r) .866)]))

; Because react.dom does not have an entry for 'use' so create one here.
(def dom-use (js/React.createFactory "use"))
(def dom-image (js/React.createFactory "image"))
(def dom-textpath (js/React.createFactory "textpath"))

(defn intersect-track
  [tile edges upgrademaps]
  (if-not (nil? upgrademaps)
  (let [ [tiles upgrades] (first upgrademaps)
         tile-edges (tiles tile) ]
    (if-not (nil? tile-edges)
      (upgrades (into tile-edges edges))
      (recur tile edges (next upgrademaps))))))

(defn upgrade-to
  "Determine tile and orient to upgrade a tile to when adding a paths joining edges."
  [{ :keys [pos tile orient] :as props} edges]
  (let [ real (vec (keep identity edges))
        [merge-list new-props] (upg/upgrade tile orient real)]
      (-> props
        (merge (if (:tile new-props) new-props))
        (assoc :station
               (reduce (fn [a [k v]] (update a (get merge-list k k) into v)) {} (:station props))))))

(defn insert-tile-direct
  "Add a path to a tile."
  [[this pos-entry] edges]
  (if (nil? (pos-entry :color))
      (let [upgrade (upgrade-to pos-entry edges)]
        (if (not= (:tile pos-entry) (:tile upgrade))
          (do
            (om/transact! this `[(hex/lay-tile ~(merge upgrade {:overlay {}})) [:tileinv/by-tile ~(:tile pos-entry)] [:tileinv/by-tile ~(:tile upgrade)]])
            true)
        false))))

(defn insert-tile
  "Add a path to a tile."
  [[this pos-entry] e1 e2]
  (insert-tile-direct [this pos-entry] [e1 (mod (+ 3 e2) 6)]))

(defn token-city
  [this pos station]
  (if (:ephemeral/operating @(om/app-state (om/get-reconciler this)))
    (om/transact! this `[(hex/lay-token ~{:pos pos :station station
                                          :operating (:ephemeral/operating @(om/app-state (om/get-reconciler this)))})])))

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
               direction (utils/find-direction lastpos pos (:rotate @(om/app-state (om/get-reconciler this))))
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


(defn hex-hide-edit
  [this]
     (om/transact! (om/get-reconciler this) `[(draw/edit-done)]))

(defn hex-edge
  [ this to-edit props in pos ]
      (let [in' (if (keyword? pos) (remove keyword? in) in)
            in (if (some #{pos} in) (remove #{pos} in) (conj in' pos))
            in (keep identity in)
            edges (remove keyword? in)
            new-tile (some #(if (keyword? %) %) in)
            tile (if new-tile (name new-tile) (:tile props))
            new-props (assoc props :tile tile)
            insert (insert-tile-direct [to-edit new-props] edges)    ; No need to wrap as it will be wrapped later.
            ]
        (if insert
         (om/transact! this '[(draw/edit-done) :ephemeral/draw])
         (om/transact! this `[(draw/edit-edge ~{:this to-edit :props props :in in}) :ephemeral/draw]))))

(defn hex-edit
  [props pos this]
  (hex-edge (om/get-reconciler this) this props [] nil))
  ;(om/transact! (om/get-reconciler this) `[(draw/edit-edge ~{:this this :props props :in []})]))

(defn draw-text
  [loc text [bg-color-default fg-color-default]]
   (let [
         [loc-start loc-end bg-color fg-color] (if (vector? loc) loc [loc nil nil nil])
         [x-start y-start] (map (partial * 50) (pos-in-hex loc-start))
         [x-end y-end] (map (partial * 50) (pos-in-hex loc-end))
         fg-color (or fg-color fg-color-default)
         bg-color (or bg-color bg-color-default)
         x-delta (- x-end x-start)
         y-delta (- y-end y-start)
         dist (Math/sqrt (+ (* x-delta x-delta) (* y-delta y-delta)))
         tag (rand)]
     (if-not (nil? loc)
       (if-not (= -68.75 x-end)
         (dom/g #js { :transform "scale(0.02)"}
            (dom/path #js {:id (str tag loc-start)
                           :stroke bg-color :strokeWidth 15
                           :d (str "M"x-start" "y-start"L"x-end" "y-end) })
            (dom/text #js {:fill fg-color
                           :dangerouslySetInnerHTML
                           #js {:__html
                                (str "<textPath xlink:href='#"(str tag loc-start)"' textLength='"dist"' lengthAdjust='spacingAndGlyphs'>"text"</textPath>")}}
                     (comment "Does not render correctly" (dom-textpath #js {:xlinkHref (str "#" pos loc-start)
                                         :textLength dist    ;; to a first aproximation
                                         :lengthAdjust "spacingAndGlyphs" }
                                    text))
                     ))
         (dom/text #js {:x x-start :y y-start
                        :fontSize 16 :textAnchor "middle"
                        :fill fg-color
                        :transform "scale(.02)"}
                   text)))))

(defn color-offboard
  [coordss colors values]
  (loop
    [coordss coordss
     colors colors
     values values
     xd {}]
     (let [ [rest-colors rest-values result-xd]
                     (loop
                        [coords (first coordss)
                        colors colors
                        values values
                        xd xd]
                        (let [[start end] coords]
                        (if end (recur (rest coords) (rest colors) (rest values)
                                (assoc xd [start end (first colors)] (first values)))
                            (list colors values xd))))]
        (if (empty? values)
            xd
            (recur (rest coordss) rest-colors rest-values result-xd)))))

(defui TileView
    static om/Ident
    (ident [this {:keys [pos]}] [:tile/by-pos pos])
    static om/IQuery
    (query [this] '[:pos :tile :orient :stations :overlay-punch :overlay :label :color])
    Object
    (render [this]
    (let [{:keys [tile pos orient] :as props} (om/props this)
          [row col] (utils/pos-to-rc pos)
          app-state  @(om/app-state (om/get-reconciler this))
          rotate  (:rotate app-state)
          path  (:mappath app-state)
          width (if (= rotate 30) 0.86 1.5)
          height (if (= rotate 30) 1.5 0.86)
          tile-info (@upg/track-list tile)]
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
        (into (map (fn [[loc [sym cost]]]
                 (dom-use #js {:xlinkHref (str "defs.svg#" sym)
                               :transform (str "translate(" ((pos-in-hex loc) 0) ","
                                                            ((pos-in-hex loc) 1) ")" "
                                                rotate(-" rotate ")")
                               }))
                 (:overlay props)))
        (into (map (fn [[loc [sym cost]]]
                 (dom-use #js {:xlinkHref (str "defs.svg#" sym)
                               :transform (str "translate(" ((pos-in-hex loc) 0) ","
                                                            ((pos-in-hex loc) 1) ")" "
                                                rotate(-" rotate ")")
                               }))
                 (:overlay-punch props)))
        (into (map (fn [[loc text]] (draw-text loc text [[(hex-colors (get tile-info "p.")) "black"]]))
                   (if (vector? (:label props))
                     (color-offboard (:offboard-pos app-state) (:offboard-colors app-state) (:label props))
                     (:label props))))
        (conj (draw-tile this props rotate path)))))))

(def tile-view (om/factory TileView {:keyfn :pos}))

(defui TileEditView
    static om/IQuery
    (query [this] [:ephemeral/draw :transform-track])
    Object
    (render [this]
    (let [
          {:keys [in last drawing]} (get-in (om/props this) [:ephemeral/draw])
          transform-track (get-in (om/props this) [:transform-track])
          [to-edit {:keys [pos tile] :as props}] last
          [row col] (utils/pos-to-rc pos)
          rotate  (:rotate @(om/app-state (om/get-reconciler this)))
          width (if (= rotate 30) 0.86 1.5)
          height (if (= rotate 30) 1.5 0.86)
          ]
       (if drawing
         (apply dom/g #js { :transform (str "translate(" (+ 4 (* width col)) "," (+ 4 (* height row)) ") "
                              "rotate(" rotate ") scale(1.5)")
                            :id "upgrade-select" }
             (-> [(dom-use #js {:xlinkHref (str "defs.svg#hex")
                                :transform (str "rotate(0)")
                                :fill "blue" :opacity "0.1"
                                :onClick (fn [e] (hex-hide-edit this))})]
                 (into (if (contains? transform-track tile)
                         (map (fn [[new-base [loc sym fill]]]
                                (dom-use #js {:xlinkHref (str "defs.svg#" sym)
                                              :transform (str "translate(" ((pos-in-hex loc) 0) "," ((pos-in-hex loc) 1) ")")
                                              :color (if (= in (filter #(not (= (keyword new-base) %)) in)) (or fill "white") "green")
                                              :onClick (fn [e] (hex-edge this to-edit props in (keyword new-base)))}))
                                (transform-track tile))
                         []))
                 (into (for [orient [0 1 2 3 4 5]]
                   (dom-use #js {:xlinkHref (str "defs.svg#target")
                                 :transform (str "rotate(" (* 60  (+ 3 orient)) ") translate(0,0.866)")
                                 :color (if (= in (filter #(not (= orient %)) in)) "white" "green")
                                 :onClick (fn [e] (hex-edge this to-edit props in orient))})))))))))

(def tile-edit-view (om/factory TileEditView {:keyfn :drawing}))


(defui TileSetView
    static om/Ident
    (ident [this {:keys [tile]}] [:tileinv/by-tile tile])
    static om/IQuery
    (query [this] [:tile :remaining])
    Object
    (render [this]
    (let [
          {:keys [tile remaining] :as props} (om/props this)
          rotate  (:rotate @(om/app-state (om/get-reconciler this)))
          width (if (= rotate 30) 0.86 1.5)
          height (if (= rotate 30) 1.5 0.86)
          scale 30
          ]
      (dom/svg #js {:width (* 2.5 scale) :height (* 2 scale)}
        (dom/g #js { :transform (str "translate(" scale "," scale ") scale(" scale ") rotate(" rotate ")") }
          (draw-tile this props 0))
          (dom/text #js {:x (* 2.1 scale)
                              :y scale
                              :fontSize 16 :textAnchor "middle"
                              :fill "black" }
                    (if (> 0 remaining) "∞" remaining))))))

(def tile-set-view (om/factory TileSetView {:keyfn :tile}))

(defn draw-tile
  [this {:keys [pos tile orient color] :as props} rotate path]
  (let [tile-info (@upg/track-list tile)
        color (or color (hex-colors (get tile-info "p."))) ]
(apply dom/g #js { :transform (str "rotate(" (* orient 60) ")") }
    (reduce
          (fn [a [k v]]
            (let [op (first k)
                  location (string/join "" (rest k))
                  location-real (or (tile-info (str "a" location)) [location])
                  stations (or (tile-info (str "r" location)) location-real)
                  ]
             (-> a
              (into (if (and (upg/substring? op ".cd") (sequential? v))
                      (for [track-set (if (sequential? (first v)) v (if (= 2 (count v)) [v] (map (fn [x] [(first location-real) x]) v)))]
                        (let [[e1 e2] track-set]
                          (if-not (and (number? e1) (number? e2))
                            (dom/path #js {:d (str "M" ((pos-in-hex e1) 0) " "
                                                       ((pos-in-hex e1) 1)
                                                   "L" ((pos-in-hex e2) 0) " "
                                                       ((pos-in-hex e2) 1))
                                           :stroke "black" :strokeWidth 0.1})
                            (case (- e2 e1)
                              1 (dom-use #js {:xlinkHref "defs.svg#sharp"
                                              :transform (str "rotate(" (* e1 60) ")")})
                              2 (dom-use #js {:xlinkHref "defs.svg#gentle"
                                              :transform (str "rotate(" (* e1 60) ")")})
                              3 (dom-use #js {:xlinkHref "defs.svg#straight"
                                              :transform (str "rotate(" (* e1 60) ")")})
                              4 (dom-use #js {:xlinkHref "defs.svg#gentle"
                                              :transform (str "rotate(" (* e2 60) ")")})
                              5 (dom-use #js {:xlinkHref "defs.svg#sharp"
                                              :transform (str "rotate(" (* e2 60) ")")}))))
                                       ) [] ))
              (into (case op
                "c" (into
                      (map (fn [loc co]
                             (dom/g nil
                             (dom-use #js {:xlinkHref "defs.svg#city"
                                           :transform (str "translate(" ((pos-in-hex loc) 0) "," ((pos-in-hex loc) 1) ")")
                                           :onClick (fn [e] (.stopPropagation e) (token-city this pos location))
                                           :color "white"})
                             (if co
                               (dom-image #js {:xlinkHref (str path "/" co)
                                               :height 0.5 :width 0.5
                                               :transform (str "translate(" (- ((pos-in-hex loc) 0) 0.25) "," (- ((pos-in-hex loc) 1) 0.25) ")")}))))
                           stations
                           (into (vec (get-in props [:station location])) (repeat (count stations) nil)))
                      (for [loc1 stations loc2 stations]
                        (dom/path #js {:d (str "M" ((pos-in-hex loc1) 0) " "
                                                   ((pos-in-hex loc1) 1)
                                               "L" ((pos-in-hex loc2) 0) " "
                                                   ((pos-in-hex loc2) 1))
                                       :stroke "white"
                                       :strokeWidth 0.5})))
                "d" (for [loc stations]
                       (dom-use #js {:xlinkHref "defs.svg#dit"
                                     :transform (str "translate(" ((pos-in-hex loc) 0) "," ((pos-in-hex loc) 1) ")") }))
                "t" [(draw-text (second v) (first v) [(hex-colors (get tile-info "p.")) "black"])]
                "l" [(draw-text location v [(hex-colors (get tile-info "p.")) "black"])]
                "v" (if (= 2 (count v))
                      [(dom-use #js {:xlinkHref (str "defs.svg#value" (if (> 100 (first v)) "2" "3"))
                                   :transform (str "translate(" ((pos-in-hex (second v)) 0) "," ((pos-in-hex (second v)) 1) ")") })
                     (draw-text (second v) (first v) ["white" "black"])]
                      [])
                nil)))))
            [ (dom-use #js {:xlinkHref "defs.svg#hex" :fill color}) ] tile-info))))

