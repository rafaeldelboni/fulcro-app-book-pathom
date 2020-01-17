(ns fulcro-app-book-pathom.components
  (:require [com.stuartsierra.component :as component]
            [fulcro-app-book-pathom.components.config :as config]
            [fulcro-app-book-pathom.components.routes :as routes]
            [fulcro-app-book-pathom.components.webserver :as webserver]
            [fulcro-app-book-pathom.services :as services]))

(defn build-system-map []
  (component/system-map
    :config (config/new-config)
    :routes  (routes/new-routes #'services/routes)
    :http-server (component/using (webserver/new-webserver) [:config :routes])))
