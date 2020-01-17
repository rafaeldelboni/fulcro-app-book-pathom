(ns fulcro-app-book-pathom.components.webserver
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.interceptor.helpers :refer [before]]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.log :as log]
            [fulcro-app-book-pathom.pathom.parser :as pathom-parser]))

(defn- add-system [service]
  (before (fn [context] (assoc-in context [:request :components] service))))

(defn- system-interceptors 
  "Extend to service's interceptors to include one to inject the components
  into the request object"
  [service service-map]
  (update-in service-map
             [::http/interceptors]
             #(vec (->> % (cons (add-system service))))))

(defn- base-service [routes config]
  (println (set (concat (deref routes) pathom-parser/routes)))
  {:env :prod
   ::http/type :jetty
   ::http/router :prefix-tree
   ::http/routes #(route/expand-routes (set (concat (deref routes) pathom-parser/routes)))
   ::http/allowed-origins {:creds true
                           :allowed-origins [(:cors-allowed-origins config)]}
   ::http/secure-headers {:content-security-policy-settings {:object-src "'none'"
                                                             :script-src "'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:"
                                                             :frame-ancestors "'none'"}}
   ::http/resource-path "/public"
   ::http/port            (:webserver-port config)
   ::http/host            (:webserver-host config)})

(defn- prod-init [service-map]
  (-> service-map
      http/default-interceptors))

(defn- dev-init [service-map]
  (-> service-map
      (merge {:env                   :dev
              ::http/join?           false
              ::http/secure-headers  {:content-security-policy-settings {:object-src "none"}}
              ::http/allowed-origins {:creds true
                                      :allowed-origins (constantly true)}})
      http/default-interceptors
      http/dev-interceptors))

(defn- system-init [{env :environment} service-map]
  (if (= env "dev")
    (dev-init service-map)
    (prod-init service-map)))

(defrecord WebServer [config routes storage]
  component/Lifecycle
  (start [this]
    (log/info :msg (str "Starting webserver on " (:webserver-port config)))
    (assoc this :service
           (->> (base-service (:routes routes) config)
                (system-init config)
                (system-interceptors this)
                http/create-server
                http/start)))

  (stop [this]
    (log/info :msg "Stopping webserver")
    (http/stop (:service this))
    (dissoc this :service)
    this))

(defn new-webserver []
  (map->WebServer {}))
