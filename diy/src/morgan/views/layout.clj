(ns morgan.views.layout
  (:require [net.cgrand.enlive-html :as h]
            [morgan.views.utils :as utils]
            [morgan.noir-auth-app.views.common :as common]))

(h/deftemplate layout "morgan/views/layout.html"
  [{:keys [title script-js css main top logo user-log wrapper footer]}]

  [:#title]
  (utils/maybe-content title)

  [:#script-js]
  (h/clone-for [script script-js]
                  (h/set-attr :src script))

  [:#css]
  (h/clone-for [link-css css]
               (h/set-attr :href link-css))
  
  [:#main]
  (utils/maybe-content main)
  
  [:#top]
  (utils/maybe-content top)

  [:#logo]
  (utils/maybe-content logo)

  [:#user-log]
  (if-let  [user (common/current-user)]
    (h/content (str "Hi " (:username user)))
    (utils/maybe-content user-log))

  [:#wrapper]
  (utils/maybe-substitute wrapper)

  [:#footer]
  (utils/maybe-content footer))