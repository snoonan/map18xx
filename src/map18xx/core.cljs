(ns map18xx.core
  (:require [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [clojure.string :as string]
            [map18xx.map1820 :as board]
            [map18xx.tiles :as tiles]
            [map18xx.parser :as p]
            [map18xx.utils :as utils]
            ))


(defui MapView
       static om/IQuery
       (query [this]
              [{:tiles (om/get-query tiles/TileView)}])
       Object
       (render [this]
               (let [scale 10
                     tiles (-> this om/props :tiles)
                     rotate  (:rotate board/app-state)
                     width (if (= rotate 30) 0.86 1.5)
                     height (if (= rotate 30) 1.5 0.86)
                     [mx my] (reduce #(
                               let [[y x] (utils/pos-to-rc (:pos %2))
                                    [maxx maxy] %1]
                                   [(max x maxx) (max y maxy)]
                                    ) [0 0] tiles)]
                 (dom/svg #js {:width (* (+ mx 8) width scale) :height (* (+ my 8) height scale)}
                  (dom/g #js {:transform (str "scale("scale")")}
                   (mapv tiles/tile-view tiles))))))

(def reconciler
  (om/reconciler
    {:state board/app-state
     :parser (om/parser {:read p/read :mutate p/mutate})}))

(om/add-root! reconciler
  MapView (gdom/getElement "map"))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

