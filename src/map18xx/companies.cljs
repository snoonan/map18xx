(ns map18xx.companies
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljsjs.react]
            [map18xx.map18io :as board]
            [map18xx.utils :as utils]))


(def operating-state (atom {}))

; Because react.dom does not have an entry for 'use' so create one here.
(def dom-use (js/React.createFactory "use"))
(def dom-image (js/React.createFactory "image"))

(defn select-company
  [this token]
     (om/transact! (om/get-reconciler this) `[(company/select {:select ~token})]))

(comment
(defui CompanyEditView
    static om/IQuery
    (query [this] '[:ephemeral/operating :company-list :mappath])
    Object
    (render [this]
    (let [{:keys [:ephemeral/operating :company-list] :as props} (om/props this)
          selected (:selected operating)
          ]
         (dom/ul #js { :id "company-select" }
                 (for [company company-list]
                   (dom/li #js {
                                    :textColor (:color company)
                                    :fontSize (if (= (:selected operating) (:token company)) 16 12)
                                    :onClick (fn [e] (select-company this (:token company)))} (:token company)))))))

(def company-edit-view (om/factory CompanyEditView {:keyfn :token}))
)

(defn company-edit-view
  [{:keys [:ephemeral/operating :company-list :mappath] :as props} this]
  (let [ selected (:selected operating)
    ]
       (apply dom/div #js {:style #js {:display "flex" :flexWrap "wrap"} }
                 (map (fn [company]
                    (dom/div #js { :style #js {:flexBasis 120 }}
                       (dom/img #js {:src (str mappath "/" (:token company))
                                       :height 30 :width 30
                                       :onClick (fn [e] (select-company this (:token company)))})
                       (dom/span #js { :style #js {:fontSize (if (= selected (:token company)) "x-large" "medium") } }
                                (:name company))))
                      company-list))))
