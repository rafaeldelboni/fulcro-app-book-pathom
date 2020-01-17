(ns fulcro-app-book-pathom.controllers.people.mutations
  (:require
    [fulcro-app-book-pathom.controllers.people.resolvers :refer [list-table]]
    [com.wsscode.pathom.connect :as pc]
    [io.pedestal.log :as log]))

(pc/defmutation delete-person
  [env {list-id   :list/id
        person-id :person/id}]
  ;; optional, this is how you override what symbol it responds to. Defaults to current ns.
  {::pc/sym `app.mutations/delete-person}
  (log/info :msg (str "Deleting person " person-id " from list " list-id))
  (swap! list-table
         update
         list-id
         update
         :list/people
         (fn [old-list] (filterv #(not= person-id %) old-list))))

(def mutations [delete-person])
