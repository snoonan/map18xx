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
  [{ :keys [pos tile orient] :as props} edges]
  (let [ real (vec (filter #(not (nil? %)) edges))
        [merge-list new-props] (upg/upgrade tile orient real)
        ]
      (-> props
        (merge (if (:tile new-props)
          new-props))
        (assoc :station
               (reduce (fn [a [k v]] (update a (get merge-list k k) conj v)) {} (:station props))))))

(defn insert-tile-direct
  "Add a path to a tile."
  [[this pos-entry] e1 e2]
  (if (and (not (= e1 e2)) (nil? (pos-entry :color)))
      (let [upgrade (upgrade-to pos-entry [e1 e2])
            ]
        (if (not= (:tile pos-entry) (:tile upgrade))
          (do
            (om/transact! this `[(hex/lay-tile ~(merge upgrade {:overlay {}}))])
            true)
        false))))

(defn insert-tile
  "Add a path to a tile."
  [[this pos-entry] e1 e2]
  (insert-tile-direct [this pos-entry] e1 (mod (+ 3 e2) 6)))

(defn token-city
  [this pos station]
  (om/transact! this `[(hex/lay-token ~{:pos pos :station station
                                      :operating (:ephemeral/operating @(om/app-state (om/get-reconciler this)))})]))

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

(defn hex-edit
  [props pos this]
     (om/transact! (om/get-reconciler this) `[(draw/edit-edge ~{:this this :props props :in []})]))

(defn hex-edge
  [ this to-edit props in pos ]
      (let [in' (if (keyword? pos) (remove keyword? in) in)
            in (if (some #{pos} in) (remove #{pos} in) (conj in' pos))
            edges (remove keyword? in)
            new-tile (some #(if (keyword? %) %) in)
            tile (if new-tile (name new-tile) (:tile props))
            new-props (assoc props :tile tile)
            insert (apply insert-tile-direct [to-edit new-props] edges)    ; No need to wrap as it will be wrapped later.
            ]
        (if insert
         (om/transact! this '[(draw/edit-done)])
         (om/transact! this `[(draw/edit-edge ~{:this to-edit :props props :in in})]))))

(defui TileView
    static om/Ident
    (ident [this {:keys [pos]}] [:tile/by-pos pos])
    static om/IQuery
    (query [this] '[:pos :tile :orient :stations :overlay-punch :overlay :label :color])
    Object
    (render [this]
    (let [{:keys [tile pos orient] :as props} (om/props this)
          [row col] (utils/pos-to-rc pos)
          rotate  (:rotate @(om/app-state (om/get-reconciler this)))
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
        (into (map (fn [[loc sym]]
                 (dom-use #js {:xlinkHref (str "defs.svg#" sym)
                               :transform (str "translate(" ((pos-in-hex loc) 0) ","
                                                            ((pos-in-hex loc) 1) ")" "
                                                rotate(-" rotate ")")
                               }))
                 (:overlay props)))
        (into (map (fn [[loc sym]]
                 (dom-use #js {:xlinkHref (str "defs.svg#" sym)
                               :transform (str "translate(" ((pos-in-hex loc) 0) ","
                                                            ((pos-in-hex loc) 1) ")" "
                                                rotate(-" rotate ")")
                               }))
                 (:overlay-punch props)))
        (into (map (fn [[loc text]]
                 (dom/text #js {:x (* 50 ((pos-in-hex loc) 0))
                                :y (* 50 ((pos-in-hex loc) 1))
                                :fontSize 16 :textAnchor "middle"
                                :fill "black"
                                :transform (str "scale(0.02) rotate(-" rotate ")")} text))
               (:label props)))
        (conj (draw-tile this props rotate)))))))

(def tile-view (om/factory TileView {:keyfn :pos}))

(defui TileEditView
    static om/IQuery
    (query [this] '[:ephemeral/draw])
    Object
    (render [this]
    (let [
          ; HELP! something is going on here that I don't understand. sometimes it is the value sometimes it is a tree that contains.
          {:keys [in last drawing]} (om/props this)
          {:keys [in last drawing]} (if (nil? in) (get-in (om/props this) [:ephemeral/draw]) {:in in :last last :drawing drawing})
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

(defn draw-tile
  [this {:keys [pos tile orient color] :as props}]
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
                              (dom-use (clj->js (merge {:xlinkHref "defs.svg#city"
                                                        :transform (str "translate(" ((pos-in-hex loc) 0) "," ((pos-in-hex loc) 1) ")") }
                                               (if co
                                                 {:color co}
                                                 {:color "white"
                                                  :onClick (fn [e] (.stopPropagation e) (token-city this pos location))})))))
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
                "t" [(dom/text #js {:x (* 50 ((pos-in-hex (second v)) 0))
                                    :y (* 50 ((pos-in-hex (second v)) 1))
                                    :fontSize 16 :textAnchor "middle"
                                    :fill "black"
                                    :transform "scale(.02)"} (first v))]
                "l" [(dom/text #js {:x (* 50 ((pos-in-hex location) 0))
                                    :y (* 50 ((pos-in-hex location) 1))
                                    :fontSize 16 :textAnchor "middle"
                                    :fill "black"
                                    :transform "scale(.02)"} v)]
                nil)))))
            [ (dom-use #js {:xlinkHref "defs.svg#hex" :fill color}) ] tile-info))))

