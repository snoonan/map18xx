(ns map18xx.core
  (:require [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [clojure.string :as string]
            [clojure.walk :as walk]
            [map18xx.map1820 :as board]
            [map18xx.companies :as companies]
            [map18xx.tiles :as tiles]
            [map18xx.parser :as p]
            [map18xx.utils :as utils]
            [map18xx.timetravel :as timetravel]
            [map18xx.upg :as upg]
            ))


(defui MapView
       static om/IQuery
       (query [this]
              [{:tiles (om/get-query tiles/TileView)}
               {:companies [:name :color]}
               {:ephemeral/draw (om/get-query tiles/TileEditView)}
               {:ephemeral/operating [:selected]}])
       Object
       (render [this]
               (let [scale 30
                     tiles (-> this om/props :tiles)
                     companies (-> this om/props :companies)
                     rotate  (:rotate board/app-state)
                     width (if (= rotate 30) 0.86 1.5)
                     height (if (= rotate 30) 1.5 0.86)
                     edit-select (tiles/tile-edit-view (-> this om/props :ephemeral/draw))
                     [mx my] (reduce #(
                               let [[y x] (utils/pos-to-rc (:pos %2))
                                    [maxx maxy] %1]
                                   [(max x maxx) (max y maxy)]
                                    ) [0 0] tiles)]
                (dom/div nil
                 (dom/svg #js {:width (* (+ mx 8) width scale) :height (* (+ my 8) height scale)}
                  (apply dom/g #js {:transform (str "scale("scale")")}
                   (conj (mapv tiles/tile-view tiles) edit-select )))
                 (companies/company-edit-view {:company-list companies :ephemeral/operating { :selected (-> this om/props :ephemeral/operating :selected) }} this)
                 ))))

(def reconciler
  (om/reconciler
    {:state board/app-state
     :parser (om/parser {:read p/read :mutate p/mutate})}))

(om/add-root! reconciler
  MapView (gdom/getElement "map"))

(defn ^:export game_cmd
  "Interface to the multi-player js. A command has happened, so pass it on."
  [c s]
  (let [fields (string/split s ":") ]
    ; (if (= "lay-tile" (first fields))
    (let [[pos tile orient] (rest fields) ]
    (om/merge! reconciler {[:tile/by-pos  pos] {:pos pos, :tile tile, :orient orient :overlay [] }}))))

;; Time travel UI
(timetravel/watch-state (om/app-state reconciler) )

(defui TTmomentView
    Object
    (render [this]
    (let [{:keys [moment current] :as props} (om/props this)
          ]
      (dom/td #js {:onClick #(timetravel/goto moment (om/app-state reconciler))
                   :className (if current "selected" "notselected") }  (apply + moment)))))
      ;(dom/td nil (string/join "." moment)))))

(def moment-view (om/factory TTmomentView))

(defui TTspanView
    Object
    (render [this]
    (let [{:keys [span] :as props} (om/props this)
          ]
      (dom/td #js {:colSpan span :className "right"} "|"))))

(def span-view (om/factory TTspanView {:keyfn :path}))

(defui TTView
  static om/IQuery
  (query [this]
         [:current-path])
  Object
  (render [this]
          (dom/div nil
             (let [path (:current-path (om/props this))  ]
               (if-not (and (= (count path) 1) (= (first path) 0))
                 (dom/button #js {:onClick #(timetravel/undo (om/app-state reconciler))} "Undo")
                 ))
             (dom/button #js {:onClick #(timetravel/redo (om/app-state reconciler))} "Redo")
             (dom/br nil)
             (dom/table nil
               (apply dom/tbody nil
                (map #(apply dom/tr nil %) (timetravel/transform-to-grid
                                   #(span-view {:span %3 :path (string/join "-" [%1 %2])})
                                   #(moment-view {:moment %1 :current %2})
                                 @timetravel/app-history)))))))

(def tt-reconciler
  (om/reconciler
    {:state timetravel/app-history
     :parser (om/parser {:read p/read})}))

(om/add-root! tt-reconciler
  TTView (gdom/getElement "tt"))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

