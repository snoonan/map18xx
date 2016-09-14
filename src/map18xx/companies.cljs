(ns map18xx.companies
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljsjs.react]
            [map18xx.map18io :as board]
            [map18xx.utils :as utils]
            ))

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
    (query [this] '[:ephemeral/operating :company-list])
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
                       (dom-image #js {:xlinkHref (str (:token company) ".png")
                                       :height 1 :width 1
                                       :onClick (fn [e] (select-company this (:token company)))})
                       (dom/g #js {:transform "translate(1.1, 0.5)"} 
                         (dom/text #js {:x 0 :y 0
                                       :fontSize (if (= selected (:token company)) 20 16)
                                       :fill "black"
                                       :transform "scale(.02)"
                                        } (:name company)))))
                      company-list)))))
