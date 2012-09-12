(ns morgan.noir-auth-app.models.mailer
  (:require [postal.core :as postal]))

; TODO: handle the case when sending fails (ex. when SMTP_SERVER, etc are not configured)
(defn send-email [{:keys [from to subject body]}]
  ; If your destination supports TLS instead, you can use :tls.
  ; This will default to port 25, however, so if you need a
  ; different one make sure you supply :port. (It's common for ISPs
  ; to block outgoing port 25 to relays that aren't theirs. Gmail
  ; supports SSL & TLS but it's easiest to just use SSL since
  ; you'll likely need port 465 anyway.)
  ; https://github.com/drewr/postal
  ;
  (println "Send an email")
  (postal/send-message #^{ :host "smtp.gmail.com"
                           :user "morganthesecretary@gmail.com"
                           :pass "nobody94"
                           :ssl true }
                       { :from from
                         :to to
                         :subject subject
                         :body body }))
