(ns fulcro-app-book-pathom.components.config
  (:require [com.stuartsierra.component :as component]
            [config.core :refer [env]]))

(defrecord Config [config]
  component/Lifecycle
  (start [this]
    (assoc this :config config))
  (stop  [this]
    (dissoc this :config)))

(defn new-config [] (map->Config env))
