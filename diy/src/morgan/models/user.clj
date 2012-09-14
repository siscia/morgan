(ns morgan.models.user
  (:require [noir.util.crypt :as crypy]
            [monger.collection :as mc]
            [noir.session :as session]))

(defn valid-new-user [{:keys [username] :as user}]
  (not (mc/any? "user" {:username username})))

(defn add-user [{:keys [username password email] :as user}]
  (mc/insert "user" (update-in user [:password] crypy/encrypt)))

(defn auth? [{:keys [username password] :as user}]
  (if-let [pass (-> (mc/find-one-as-map "user" {:username username}) :password)]
    (if (crypy/compare password pass)
      true
      false)
    false))

(defn login-user [{:keys [username password] :as user}]
  (session/put! :username username))

(defn find-user [username]
  (mc/find-one-as-map "user" {:username username}))

(defn logout-user []
  (session/clear!))

(defn is-loged? []
  (if-not (nil? (session/get :username))
    true
    false))
