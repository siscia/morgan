(ns morgan.noir-auth-app.views.home
  (:use [noir.core]
       [hiccup.page-helpers :only [link-to]])
  (:require [morgan.noir-auth-app.views.common :as common]
            [morgan.noir-auth-app.models.user :as users]
            [morgan.noir-auth-app.i18n :as i18n]
            [noir.session :as session]
            [net.cgrand.enlive-html :as html])
  (:use [morgan.views.layout :only [layout]]))

(defpage "/" []
  (common/layout (i18n/translate :home-page-title)
      (when-let [notice (session/flash-get)] [:p.notice notice])
      [:p (if-let [user (common/current-user)]
              (str "Hello " (:username user) "!")
              "Welcome!")]))

(html/defsnippet home "morgan/noir_auth_app/views/home.html" [:#home]
  [user]
  
  [:#greets]
  (let [message
        (if user
          (str "Hi " (:username user) "!\n Welcome back to Morgan")
          "Welcome to Morgan")]
    (html/content message)))

(defpage "/home" []
  (layout
   {:title "Morgan home"
    :wrapper (home (common/current-user))}))
