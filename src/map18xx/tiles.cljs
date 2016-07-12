(ns map18xx.tiles
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljsjs.react]
            [map18xx.upgrade :as upgrade]
            [map18xx.map1820 :as board]
            [map18xx.utils :as utils]
            ))

(def draw-state (atom {}))

; Because react.dom does not have an entry for 'use' so create one here.
(def dom-use (js/React.createFactory "use"))

(defn intersect-track
  [tile edges upgrademaps]
  (if-not (nil? upgrademaps)
  (let [
        [tiles upgrades] (first upgrademaps)
         tile-edges (tiles tile)
         _ (println tile (into tile-edges edges))]
    (if-not (nil? tile-edges)
      (upgrades (into tile-edges edges))
      (recur tile edges (next upgrademaps))))))

(defn upgrade-to
  "Determine tile and orient to upgrade a tile to when adding a path from e1 to e2."
  [{ :keys [pos tile orient]} entry-points]
  (let [ 
         exits (if-not (nil? (get entry-points 1)) (assoc entry-points 1 (mod (+ 3 (get entry-points 1)) 6)) entry-points)   ; 3+ to translate from second enter to exit edge
        real (vec (filter #(not (nil? %)) exits))
         rotated (map #(mod (- % orient) 6) real)   ; normalize input to tile rotation
         ordered (vec (sort rotated))
        [newtile neworient] (or (intersect-track tile ordered upgrade/upgrademaps) [nil nil])
        ]
      (if (nil? newtile)
        nil
        {:pos pos :tile newtile :orient (mod (+ orient neworient) 6)})))

(defn insert-tile
  "Add a path to a tile."
  [[this pos-entry] e1 e2]
  (do (println "insert for" (:pos pos-entry))
  (if (not (= e1 (mod (+ 3 e2) 6)))
      (om/transact! this `[(hex/lay-tile ~(upgrade-to pos-entry [e1 e2]))]))))

(defn hex-down
  [pos-entry pos this]
  (do (println "down" pos)
  (swap! draw-state (fn []
                        {:in [nil pos]  :last [this pos-entry] :drawing true}))))

(defn hex-up
  []
  (do (println "up")
  (swap! draw-state assoc-in [:drawing] false)))


(defn hex-enter
  [pos-entry pos this]
  (do (println "enter" (:drawing @draw-state) pos)
    (if (:drawing @draw-state)
      (let [lastpos ((:in @draw-state) 1)
               direction (utils/find-direction lastpos pos (:rotate board/app-state))
               newdraw {:in [((:in @draw-state) 1) pos] :from direction :last [this pos-entry] :drawing true}
               ]
           (do
             (insert-tile (:last @draw-state) (:from @draw-state) (:from newdraw))
             (insert-tile (:last @draw-state) nil (:from newdraw))      ; just the exit leg matters. Do this AFTER testing for both legs.
             (insert-tile (:last newdraw) nil (+ 3 (:from newdraw)))    ; No need to wrap as it will be wrapped later.
             (swap! draw-state (fn [] newdraw))))
      (swap! draw-state assoc-in [:last] [this pos-entry]))))

(defn cycle-plain
  [org-tile pos this]
  (if-let [
        tile (condp == org-tile
                "t500"  "tbig"
                "tbig"  "t503"
                "t503"  "t501"
                "t501"  "t500"
                :default nil
                )
        ]
     (om/transact! this `[(hex/lay-tile ~{:pos pos :tile tile :orient 0})])))

(defui TileView
    static om/Ident
    (ident [this {:keys [pos]}] [:tile/by-pos pos])
    static om/IQuery
    (query [this] '[:pos :tile :orient])
    Object
    (render [this]
    (let [{:keys [tile pos orient] :as props} (om/props this)
          [row col] (utils/pos-to-rc pos)
          rotate  (:rotate board/app-state)
          width (if (= rotate 30) 0.86 1.5)
          height (if (= rotate 30) 1.5 0.86)
          ]
      (dom/g #js { :transform (str "translate("
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
                      (fn [e] (cycle-plain tile pos this))
                  }
        (dom-use #js {:xlinkHref (str "defs.svg#" tile)
                  :transform (str "rotate(" (* orient 60) ")")}
                  (str orient ", points: " pos))))))

(def tile-view (om/factory TileView {:keyfn :pos}))
