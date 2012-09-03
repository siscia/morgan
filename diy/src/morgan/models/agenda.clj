(ns morgan.models.agenda
  (:require [monger.collection :as mc]
            [morgan.models.user :as u])
  (:use [monger.operators]))

(defn add-contact [username kind contact]
  (let [oid (:_id (u/find-user username))]
    (mc/update-by-id "agenda" oid {$addToSet {kind contact}})))

(defn get-contacts [username kind]
  (let [oid (:_id (u/find-user username))]
    (kind (mc/find-map-by-id "agenda" oid))))

(defn delete-contact [username kind contact]
  (let [oid (:_id (u/find-user username))]
    (mc/update-by-id "agenda" oid {$pullAll {kind contact}})))
