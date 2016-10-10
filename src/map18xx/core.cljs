(ns map18xx.core
  (:require [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [clojure.string :as string]
            [clojure.walk :as walk]
            [cljs.tools.reader :as r]
            [ajax.core :refer [GET]]
            [map18xx.map1820 :as board]
            [map18xx.companies :as companies]
            [map18xx.tiles :as tiles]
            [map18xx.parser :as p]
            [map18xx.utils :as utils]
            [map18xx.timetravel :as timetravel]
            [map18xx.upg :as upg]))


(def app-state {})

(defn load-game-data
  [this path response]
  (let [game-data (r/read-string response)]
    (om/merge! (om/get-reconciler this)
            (assoc game-data :inventory (upg/add-tiles (:track-list game-data)) :mappath path))))

(defui MapView
       static om/IQuery
       (query [this]
              [{:tiles (om/get-query tiles/TileView)}
               {:companies [:name :color]}
               :mappath
               {:inventory (om/get-query tiles/TileSetView)}
               ;(om/get-query tiles/TileEditView)
               :ephemeral/draw :transform-track
               {:ephemeral/operating [:selected]}])
       Object
       (render [this]
               (let [scale 30
                     tiles (-> this om/props :tiles)
                     companies (-> this om/props :companies)
                     mappath (-> this om/props :mappath)
                     editting (-> this om/props :ephemeral/draw)
                     full-tile-set (upg/tile-sort (-> this om/props :inventory))
                     new-tile (some #(if (keyword? %) %) (-> editting :in))
                     edges (remove keyword? (-> editting :in))
                     basetile (if new-tile (name new-tile) (-> editting :last (get 1) :tile))
                     rotate  (:rotate board/app-state)
                     width (if (= rotate 30) 0.86 1.5)
                     height (if (= rotate 30) 1.5 0.86)
                     edit-select (tiles/tile-edit-view
                                   {:ephemeral/draw (-> this om/props :ephemeral/draw)
                                    :transform-track (-> this om/props :transform-track)})
                     [mx my] (reduce #(
                               let [[y x] (utils/pos-to-rc (:pos %2))
                                    [maxx maxy] %1]
                                   [(max x maxx) (max y maxy)]
                                    ) [0 0] tiles)]
                 (if (empty? tiles)
                   (dom/div nil
                      "variant description location: "
                      (dom/input #js {:type "text"
                                      :ref "mapdata"
                                      })
                      (dom/button #js {
                                      :onClick (fn [e]
                                                 (let [path (.. (dom/node this "mapdata") -value)]
                                                  (GET
                                                    (str path "/map.edn")
                                                    {:handler (partial load-game-data this path)})))
                                      } "Load"))
                (dom/div #js {:style #js {:display "flex"  :justifyContent "space-between"}}
                 (dom/div #js {:style #js {:display "flex" :flexDirection "column"} }
                   (companies/company-edit-view {:company-list companies
                                                 :ephemeral/operating {:selected (-> this om/props :ephemeral/operating :selected)}
                                                 :mappath mappath} this)
                   (let [tile-options (if (:drawing editting)
                                        (upg/upgrade-options basetile
                                                      (-> editting :last (get 1) :orient)
                                                      edges)
                                        [])
                         tile-set-list (if (:drawing editting)
                                          (reduce (fn [a v] (conj a (first v))) [] tile-options)
                                          (map #(% :tile) full-tile-set))
                         tile-set (filter #(some (partial = (:tile %)) tile-set-list) full-tile-set)]
                       (dom/div #js {:style #js {:display "flex" :flexWrap "wrap"} }
                                (mapv #(tiles/tile-set-view
                                         (om/computed %
                                         {:options (first (filter
                                                        (fn [v] (and (= (:tile %) (first v))
                                                                     (= 1 (count (second v)))))
                                                        tile-options))
                                          :ephemeral/draw editting}))
                                  tile-set)))
                 )
                 (dom/svg #js {:width (* (+ mx 8) width scale) :height (* (+ my 8) height scale) :overflow "scroll"}
                  (apply dom/g #js {:transform (str "scale("scale")")}
                   (conj (mapv tiles/tile-view tiles) edit-select )))
                 )))))

(def reconciler
  (om/reconciler
    {:state app-state
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

