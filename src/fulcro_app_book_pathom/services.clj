(ns fulcro-app-book-pathom.services
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]))

(defn- response!
  [result status]
   (-> result
       ring-resp/response
       (ring-resp/status status)))

(defn get-version
  [{{config :config} :components}]
  (response! {:version (:simul-version config)} 200))

(def common-interceptors
  [(body-params/body-params) http/json-body])

(def routes
  #{["/version" :get (conj common-interceptors `get-version)]})
