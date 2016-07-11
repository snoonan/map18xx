(ns map18xx.tiles
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [map18xx.upgrade :as upgrade]
            [map18xx.map1820 :as board]
            [map18xx.utils :as utils]
            ))

(def draw-state (atom {}))


(defn intersect-track
  [tile e1 e2 upgrademaps]
  (if-not (nil? upgrademaps)
  (let [
        [tiles upgrades] (first upgrademaps)
         tile-edges (tiles tile)]
    (if-not (nil? tile-edges)
      (upgrades (into tile-edges [e1 e2]))
      (recur tile e1 e2 (next upgrademaps))))))

(defn upgrade-to
  "Determine tile and orient to upgrade a tile to when adding a path from e1 to e2."
  [{ :keys [pos tile orient]} e1 _e2]
  (let [ e2 (mod (+ 3 _e2) 6)   ; 3+ to translate from second enter to exit edge
        [re1 re2] [(mod (- e1 orient) 6) (mod (- e2 orient) 6)]   ; normalize input to tile rotation
        [se1 se2] (if (< re1 re2) [re1 re2] [re2 re1])
        [newtile neworient] (or (intersect-track tile se1 se2 upgrade/upgrademaps) [tile 0])
        ]
      {:pos pos :tile newtile :orient (mod (+ orient neworient) 6)}))

(defn insert-tile
  "Add a path to a tile."
  [[this pos-entry] e1 e2]
  (if (not (or (nil? e1) (nil? e2) (= e1 (mod (+ 3 e2) 6))))
      (om/transact! this `[(hex/lay-tile ~(upgrade-to pos-entry e1 e2))])))

(defn hex-down
  [pos]
     (swap! draw-state (fn [] { :drawing true :in [ nil pos]})))

(defn hex-up
  []
     (swap! draw-state assoc-in [:drawing] false))


(defn hex-enter
  [pos-entry pos this]
    (if (:drawing @draw-state)
      (let [lastpos ((:in @draw-state) 1)
               direction (utils/find-direction lastpos pos (:rotate board/app-state))
               newdraw {:in [((:in @draw-state) 1) pos] :from direction :last [this pos-entry] :drawing true}
               ]
           (do
             (insert-tile (:last @draw-state) (:from @draw-state) (:from newdraw))
             (swap! draw-state (fn [] newdraw))))
      (swap! draw-state assoc-in [:last] [this pos-entry])))

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
          scale   (:scale board/app-state)
          width (if (= rotate 30) (* 0.86 scale) (* 1.5 scale))
          height (if (= rotate 30) (* 1.5 scale) (* 0.86 scale))
          ]
      (dom/g #js { :transform (str "translate("
                              (+ 40 (* width col)) ","
                              (+ 40 (* height row)) ")"
                              "rotate(" rotate ")")
                   :onMouseDown
                      (fn [e] (hex-down pos))
                   :onMouseEnter
                      (fn [e] (hex-enter props pos this))
                   :onMouseUp
                      (fn [e] (hex-up))
                   :onClick
                      (fn [e] (cycle-plain tile pos this))
                  }
        (dom/use #js {:xlinkHref (str "defs.svg#" tile)
                  :transform (str "scale("scale") rotate(" (* orient 60) ")")}
                  (str orient ", points: " pos))))))

(def tile-view (om/factory TileView {:keyfn :pos}))
