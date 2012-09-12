(ns morgan.noir-auth-app.config)

(def app-name "morgan.noir-auth-app")

(def emails-from (get (System/getenv) "EMAILS_FROM" "morganthesecretary@gmail.com"))
(def contact-email (get (System/getenv) "CONTACT_EMAIL"))
