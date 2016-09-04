(ns map18xx.parser
  (:require [om.next :as om :refer-macros [defui]]
            [clojure.string :as string]
            [map18xx.tiles :as tiles]))

(defmulti read om/dispatch)

(defmethod read :default
    [{:keys [state] :as env} key params]
      (let [st @state]
            (if-let [[_ value] (find st key)]
                    {:value value}
                    {:value :not-found})))

(defmethod read :tiles
    [{:keys [state] :as env} key params]
      { :value (into [] (map #(get-in @state %) (get @state key))) })

(defmethod read :inventory
    [{:keys [state] :as env} key params]
      { :value (into [] (map #(get-in @state %) (get @state key))) })

(defmulti mutate om/dispatch)

(defmethod mutate 'hex/lay-tile
  [{:keys [state] :as env} _ {:keys [pos tile orient] :as props}]
    (if (not (nil? tile))
      (if-let [new-state (tiles/lay-tile @state props)]
      {:value {:keys [[:tileinv/by-tile tile] [:tileinv/by-tile (get-in @state [:tile/by-pos pos :tile])]]}
       :action
       (fn [] (js/game_cmd_send (string/join ":" ["lay-tile" pos tile orient]))
              (reset! state new-state))})))

(defmethod mutate 'hex/lay-token
  [{:keys [state] :as env} _ {:keys [pos station operating]}]
   {:action
       (fn [] (js/game_cmd_send (string/join ":" ["lay-token" pos station (:selected operating)]))
              (swap! state update-in [:tile/by-pos pos :station station] conj (:selected operating) ))})

(defmethod mutate 'draw/edit-done
  [{:keys [state] :as env} _ _]
   {:action
    (fn [] (swap! state assoc-in [:ephemeral/draw] {:in [] :last nil :drawing false}))})

(defmethod mutate 'draw/edit-edge
  [{:keys [state] :as env} _ {:keys [this props in]}]
   {:action
    (fn [] (swap! state assoc-in [:ephemeral/draw] {:in in :last [this props] :drawing true}))})

(defmethod mutate 'company/select
  [{:keys [state] :as env} _ {:keys [select] :as prop}]
   {:action
    (fn [] (swap! state assoc-in [:ephemeral/operating] {:selected select}))})
