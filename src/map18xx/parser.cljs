(ns map18xx.parser
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [map18xx.utils :as utils]
            [map18xx.tiles :as tiles]))

(defmulti read om/dispatch)

(defmethod read :tiles
    [{:keys [state] :as env} key params]
      { :value (into [] (map #(get-in @state %) (get @state key))) })

(defmulti mutate om/dispatch)

(defmethod mutate 'hex/lay-tile
  [{:keys [state] :as env} _ {:keys [pos tile orient]}]
    (if (not (nil? tile))
      {:action
       (fn [] (swap! state assoc-in [:tile/by-pos pos] {:pos pos :tile tile :orient orient}))}))
