(ns map18xx.companies
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljsjs.react]
            [map18xx.map1820 :as board]
            [map18xx.utils :as utils]
            ))

(def operating-state (atom {}))

; Because react.dom does not have an entry for 'use' so create one here.
(def dom-use (js/React.createFactory "use"))

(defn select-company
  [this name]
     (om/transact! (om/get-reconciler this) `[(company/select {:select ~name})]))

(comment
(defui CompanyEditView
    static om/IQuery
    (query [this] '[:ephemeral/operating :company-list])
    Object
    (render [this]
    (let [{:keys [:ephemeral/operating :company-list] :as props} (om/props this)
          selected (:selected operating)
          _ (prn (om/props this))
          ]
         (dom/ul #js { :id "company-select" }
                 (for [company company-list]
                   (dom/li #js {
                                    :textColor (:color company)
                                    :fontSize (if (= (:selected operating) (:name company)) 16 12)
                                    :onClick (fn [e] (select-company this (:name company)))} (:name company)))))))

(def company-edit-view (om/factory CompanyEditView {:keyfn :operating}))
)

(defn company-edit-view
  [{:keys [:ephemeral/operating :company-list] :as props} this]
  (let [ selected (:selected operating)
    ]
      (dom/svg #js { :width 120 :height (* 60 (count company-list)) }
               (apply dom/g #js {:transform "scale(30)" }
                 (map-indexed (fn [i company]
                    (dom/g #js {:transform (str "translate(1," (+ 1 (* 2 i)) ")")}
                     (dom-use #js {
                                 :xlinkHref "defs.svg#target"
                                 :transform (str "scale(" (if (= selected (:name company)) 1.4 1) ") ")
                                 :color (:token company)
                                 :onClick (fn [e]
                                            (select-company this (:name company)))})
                     (dom/text #js {:x 55 :y 0
                                   :fontSize (if (= selected (:name company)) 20 16) :textAnchor "middle"
                                   :fill "black"
                                   :transform "scale(.02)"
                                    } (:name company))))
                      company-list)))))
