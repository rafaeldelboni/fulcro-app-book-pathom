(ns fulcro-app-book-pathom.core
  (:require [io.pedestal.service-tools.dev :as dev]
            [fulcro-app-book-pathom.components :as components]
            [fulcro-app-book-pathom.server :as server])
  (:gen-class))

(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (-> (components/build-system-map)
      server/start-system!))

(defn run-dev
  "The entry-point for 'lein run-dev'"
  []
  (dev/watch) ;; auto-reload namespaces only in run-dev / repl-start
  (-main))
