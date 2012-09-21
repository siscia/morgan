(ns morgan.noir-auth-app.views.login
  (:require [net.cgrand.enlive-html :as html]
            [morgan.noir-auth-app.views.common :as common]
            [morgan.noir-auth-app.models.user :as users]
            [noir.session :as session]
            [morgan.views.utils :as utils]
            [noir.validation :as vali]
            [morgan.noir-auth-app.i18n :as i18n])
  (:use [morgan.views.layout :only [layout]]
        [morgan.noir-auth-app.i18n :only [translation]]
        [noir.core]
        [morgan.noir-auth-app.views.users :only [save-user-info-in-session]]
        [noir.response :only [redirect]]))

(html/defsnippet login "morgan/noir_auth_app/views/login.html" [:#login]
  [error notes]

  [:#error]
  (if error
    (html/content error))

  [:#note]
  (if notes 
    (html/clone-for [note notes]
                    (html/content note))
    (html/content)))

(defpage "/log" []
  (if (session/get :user-id)
    (redirect "/")
    (layout
     {:title "Login with enlive"
      :wrapper (login (apply str (translation :activation-code-not-found)) nil)})))


(defpage [:post "/log"] {:as credentials} 
  (if-let [user (users/login! credentials)]
    (do
      (save-user-info-in-session user)
      (common/redirect-back-or-default "/"))
    (layout
     {:title "Login with enlive"
      :wrapper (login (if (vali/errors? :activation_code)
                        (common/error-text
                         (i18n/translate (vali/get-errors :activation_code)
                                         (users/find-by-username-or-email
                                          (:username-or-email credentials))))
                        (vali/get-errors :password))
                      (session/flash-get))})))