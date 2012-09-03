(ns morgan.models.email
  (:use [postal.core :only [send-message]])
  (:require [morgan.models.task :as task]
            [monger.collection :as mc]))

(defn send-email [address subject body]
  (send-message ^{:host "smtp.gmail.com"
                  :user "morganthesecretary@gmail.com"
                  :pass "nobody94"
                  :ssl true}
                {:from "morganthesecretary@gmail.com"
                 :to address
                 :subject subject
                 :body body}))

(defn remember-email [tasks]
  (let [users (map #(mc/find-map-by-id "user" % ) (map :id_user tasks))
        address (map :email users)
        body (map :task tasks)
        subject (repeat "Morgan remember you that:")]
    (doall
     (map send-email address subject body))
    (println "I just send " (count tasks) "email, to " address)))