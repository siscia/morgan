(ns morgan.models.email
  (:use [postal.core :only [send-message]])
  (:require [morgan.models.task :as task]
            [monger.collection :as mc]))

(defn send-email [to subject body]
  (send-message ^{:host "smtp.gmail.com"
                  :user "morganthesecretary@gmail.com"
                  :pass "nobody94"
                  :ssl true}
                {:from "morganthesecretary@gmail.com"
                 :to to
                 :subject subject
                 :body body})
  (println "I send email" " to " to " subject " subject " body " body))

(defn remember-email [tasks]
  (let [users (map #(mc/find-map-by-id "users" % ) (map :id_user tasks))
        address (map :email users)
        subject (repeat "Morgan remember you that:")
        body (map :task tasks)
        info-send (map (fn [address subject body]
                         {:to address
                          :subject subject
                          :body body}) address subject body)]
    (doall
     (map send-email address subject body))
    (println "I just send " (count tasks) "email, to " address)))