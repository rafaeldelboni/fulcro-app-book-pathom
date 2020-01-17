(ns fulcro-app-book-pathom.pathom.parser
  (:require
    [fulcro-app-book-pathom.controllers.people.resolvers :as people.res]
    [fulcro-app-book-pathom.controllers.people.mutations :as people.mut]
    [com.wsscode.pathom.core :as p]
    [com.wsscode.pathom.connect :as pc]
    [com.wsscode.pathom.profile :as pp]
    [pathom.pedestal :refer [pathom-routes make-parser]]))

(def resolvers [people.res/resolvers people.mut/mutations])

(def pathom-parser
  (p/parser {::p/env     {::p/reader               [p/map-reader
                                                    pc/reader2
                                                    pc/ident-reader
                                                    pc/index-reader
                                                    p/env-placeholder-reader
                                                    ]
                          ;::pc/resolver-dispatch   pc/resolver-dispatch-embedded
                          ;::pc/mutate-dispatch     pc/mutation-dispatch-embedded
                          ::p/placeholder-prefixes #{">"}
                          ::pc/mutation-join-globals [:tempids]}
             ::p/mutate  pc/mutate
             ::p/plugins [(pc/connect-plugin {::pc/register resolvers})
                          p/error-handler-plugin
                          p/request-cache-plugin
                          pp/profile-plugin]}))

(def routes (pathom-routes {:oge? true :parser pathom-parser}))
