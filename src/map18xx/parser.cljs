(ns map18xx.parser
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [clojure.string :as string]
            [map18xx.utils :as utils]
            [map18xx.tiles :as tiles]))

(defmulti read om/dispatch)

(defmethod read :default
    [{:keys [state] :as env} key params]
      (let [st @state
            ]
            (if-let [[_ value] (find st key)]
                    {:value value}
                          {:value :not-found})))

(defmethod read :in
    [{:keys [state] :as env} key params]
      { :value (get-in @state [:ephemeral :draw :in]) })

(defmethod read :drawing
    [{:keys [state] :as env} key params]
      { :value (get-in @state [:ephemeral :draw :drawing]) })

(defmethod read :last
    [{:keys [state] :as env} key params]
      { :value (get-in @state [:ephemeral :draw :last]) })

(defmethod read :tiles
    [{:keys [state] :as env} key params]
      { :value (into [] (map #(get-in @state %) (get @state key))) })

(defmulti mutate om/dispatch)

(defmethod mutate 'hex/lay-tile
  [{:keys [state] :as env} _ {:keys [pos tile orient]}]
    (if (not (nil? tile))
      {:action
       (fn [] (js/game_cmd_send (string/join ":" ["lay-tile" pos tile orient]))
              (swap! state assoc-in [:tile/by-pos pos] {:pos pos :tile tile :orient orient}))}))

(defmethod mutate 'draw/edit-done
  [{:keys [state] :as env} _ {:keys [pos tile orient]}]
  {:value {:keys [[:ephemeral :draw :drawing]]}
   :action
    (fn [] (swap! state assoc-in [:ephemeral :draw] {:in [] :last nil :drawing false}))})

(defmethod mutate 'draw/edit-edge
  [{:keys [state] :as env} _ {:keys [this props in]}]
  {:value {:keys [[:ephemeral :draw :drawing]]}
   :action
    (fn [] (swap! state assoc-in [:ephemeral :draw] {:in in :last [this props] :drawing true}))})
