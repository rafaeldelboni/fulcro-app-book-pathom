(ns fulcro-app-book-pathom.server
  (:require [com.stuartsierra.component :as component]))

(def system (atom nil))

(defn start-system! [system-map]
  (->> system-map
       component/start
       (reset! system)))

(defn stop-system! []
  (swap! system #(component/stop %)))
