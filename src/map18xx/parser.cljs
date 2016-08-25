(ns map18xx.parser
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [clojure.string :as string]
            [map18xx.utils :as utils]
            [map18xx.tiles :as tiles]))

(defmulti read om/dispatch)

(defmethod read :default
    [{:keys [state] :as env} key params]
      (let [st @state ]
            (if-let [[_ value] (find st key)]
                    {:value value}
                    {:value :not-found})))

(defmethod read :ephemeral/draw
    [{:keys [state] :as env} key params]
      { :value (get-in @state [:ephemeral/draw]) })

(defmethod read :ephemeral/operating
    [{:keys [state] :as env} key params]
      { :value (get-in @state [:ephemeral/operating]) })

(defmethod read :tiles
    [{:keys [state] :as env} key params]
      { :value (into [] (map #(get-in @state %) (get @state key))) })

(defmulti mutate om/dispatch)

(defmethod mutate 'hex/lay-tile
  [{:keys [state] :as env} _ {:keys [pos tile orient] :as props}]
    (if (not (nil? tile))
      {:action
       (fn [] (js/game_cmd_send (string/join ":" ["lay-tile" pos tile orient]))
              (swap! state update-in [:tile/by-pos pos] merge props))}))

(defmethod mutate 'hex/lay-token
  [{:keys [state] :as env} _ {:keys [pos station operating]}]
   {:action
       (fn [] (js/game_cmd_send (string/join ":" ["lay-token" pos station (:selected operating)]))
              (swap! state update-in [:tile/by-pos pos :station station] conj (:selected operating) ))})

(defmethod mutate 'draw/edit-done
  [{:keys [state] :as env} _ _]
  {:value {:keys [[:ephemeral/draw :drawing]]}
   :action
    (fn [] (swap! state assoc-in [:ephemeral/draw] {:in [] :last nil :drawing false}))})

(defmethod mutate 'draw/edit-edge
  [{:keys [state] :as env} _ {:keys [this props in]}]
  {:value {:keys [[:ephemeral/draw]]}
   :action
    (fn [] (swap! state assoc-in [:ephemeral/draw] {:in in :last [this props] :drawing true}))})

(defmethod mutate 'company/select
  [{:keys [state] :as env} _ {:keys [select] :as prop}]
  {:value {:keys [:ephemeral/operating]}
   :action
    (fn [] (swap! state assoc-in [:ephemeral/operating] {:selected select}))})
