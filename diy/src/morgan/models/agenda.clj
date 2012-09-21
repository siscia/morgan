(ns morgan.models.agenda
  (:require [monger.collection :as mc]
            [morgan.models.user :as u])
  (:use [monger.operators])
  (:import [org.bson.types ObjectId]))


(defn start-new-agenda [user-id]
  (mc/insert "agenda"
             {:_id (ObjectId.)
              :id_user user-id
              :agenda []}))

;; Schema for agenda
;; {:_id (ObjectId.)
;;  :id_user user-id
;;  :agenda {name {:email [cheui@ce.com lalala@vio.it]
;;                 :phone [4173540126]}}}

(defn add-contact [user-id contact]
  (mc/update "agenda" {:id_user user-id}
             {$push {:agenda contact}}))

;; contact {:reference "Simone" :email [simone@mweb.biz
;;                            buervi@buri.com]
;;                    :phone [6416456446465]}

(defn make-contact [{:keys [name email phone]}]
  {:name name
   :reference {:email (if (vector? email) ;; HORRIBLE
                        email
                        (vector email))
               :phone (if (vector? phone)
                        phone
                        (vector phone))}})

(defn remove-contact [user-id contact]
  (mc/update "agenda" {:id_user user-id}
             {$pull {:agenda {:name contact}}}))

(defn remove-reference [user-id contact type reference]
  (mc/update "agenda" {:id_user user-id}
             {$pull {:agenda {:reference {type reference}}}}))

(defn add-reference [user-id contact type reference]
  (mc/update "agenda" {:id_user user-id}
             {$push {:agenda {:reference {type reference}}}}))
