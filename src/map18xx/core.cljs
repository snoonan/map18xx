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

(enable-console-print!)

(defui MapView
       static om/IQuery
       (query [this]
              [{:tiles (om/get-query tiles/TileView)}])
       Object
       (render [this]
               (let [tiles (-> this om/props :tiles)]
                 (dom/svg #js {:width 5000 :height 5000}
                   (mapv tiles/tile-view tiles)))))

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

