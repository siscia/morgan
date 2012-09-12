(ns morgan.models.email
  (:use [postal.core :only [send-message]])
  (:require [morgan.models.task :as task]
            [monger.collection :as mc]))

(defn send-email [{:keys [from to subject body] :or [from "morganthesecretary@gmail.com"]}]
  (send-message ^{:host "smtp.gmail.com"
                  :user "morganthesecretary@gmail.com"
                  :pass "nobody94"
                  :ssl true}
                {:from from
                 :to to
                 :subject subject
                 :body body}))

(defn remember-email [tasks]
  (let [users (map #(mc/find-map-by-id "user" % ) (map :id_user tasks))
        address (map :email users)
        subject (repeat "Morgan remember you that:")
        body (map :task tasks)
        info-send (map (fn [address subject body]
                         {:to address
                          :subject subject
                          :body body}) address subject body)]
    (doall
     (map send-email info-send))
    (println "I just send " (count tasks) "email, to " address)))