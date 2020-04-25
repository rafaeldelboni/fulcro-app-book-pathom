(ns fulcro-app-book-pathom.controllers.people.resolvers
  (:require
    [com.wsscode.pathom.connect :as pc]))

(def people-table
  "list with people"
  (atom
    {1 {:person/id 1 :person/name "Sally" :person/age 32}
     2 {:person/id 2 :person/name "Joe" :person/age 22}
     3 {:person/id 3 :person/name "Fred" :person/age 11}
     4 {:person/id 4 :person/name "Bobby" :person/age 55}}))

(def list-table
  "list with all friends and enemies relationship"
  (atom
    {:friends {:list/id     :friends
               :list/label  "Friends"
               :list/people [1 2]}
     :enemies {:list/id     :enemies
               :list/label  "Enemies"
               :list/people [4 3]}}))

(pc/defresolver person-resolver 
  "Given :person/id, this can generate the details of a person"
  [env {:person/keys [id]}]
  {::pc/input  #{:person/id}
   ::pc/output [:person/name :person/age]}
  (get @people-table id))

(pc/defresolver list-resolver
  "Given a :list/id, this can generate a list label and the people
  in that list (but just with their IDs)"
  [env {:list/keys [id]}]
  {::pc/input  #{:list/id}
   ::pc/output [:list/label {:list/people [:person/id]}]}
  (when-let [list (get @list-table id)]
    (assoc list
           :list/people (mapv (fn [id] {:person/id id}) (:list/people list)))))

(pc/defresolver friends-resolver
  "Given the output {:friends [:list/id]} without input resolve list of friends"
  [env input]
  {::pc/output [{:friends [:list/id]}]}
  {:friends {:list/id :friends}})

(pc/defresolver enemies-resolver
  "Given the output {:enemies [:list/id]} without input resolve list of enemies"
  [env input]
  {::pc/output [{:enemies [:list/id]}]}
  {:enemies {:list/id :enemies}})

(pc/defresolver test-resolver
  [env {:input/keys [id name]}]
  {::pc/input  #{:input/id
                 :input/name}
   ::pc/output [:input/result]}
  {:input/result (str id "-" name)})

; Given a :input/id and :input/name return :input/result
;(fulcro-app-book-pathom.pathom.parser/pathom-parser
  ;{}
  ;'[{([:input/id 123] {:pathom/context {:input/name 123}}) [:input/result]}])

(def resolvers [person-resolver list-resolver friends-resolver enemies-resolver test-resolver])
