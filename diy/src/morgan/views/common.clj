(ns morgan.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers]
        [hiccup.form-helpers])
  (:require [morgan.models.user :as u]
            [noir.validation :as vali]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "morgan"]
               (include-css "/css/reset.css")]
              [:body
               [:div#wrapper
                content]]))

(defpartial error-item [[first-error]]
  [:p.error first-error])

(defpartial credit-card [{:keys [number cvc month year]}]
  (include-js "https://js.stripe.com/v1/"
              "https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"
              "/js/stripe/get-payments.js") ;; stripe.js script IMPORTANT
  [:span {:class "payment-errors"}]
  (label "number" "Card Number: ")
  (text-field {:require true :size 20 :autocomplete "off" :class "card-number"} number)
  (label "cvc" "cvc: ")
  (text-field {:require true :size 4 :autocomplete "off" :class "card-cvc"} cvc)
  (label "month" "Expire month (mm): ")
  (text-field {:require true :size 2 :class "card-expiry-month"} month)
  (label "year" "Expire year (yyyy): ")
  (text-field {:require true :size 4 :class "card-expiry-year"} year))

(defpartial new-user [{:keys [user pass email]}]
  (vali/on-error :username error-item)
  (label "username" "Username: ")
  (text-field {:name "username" :required true} user)
  (vali/on-error :password error-item)
  (label "password" "Password: ")
  (text-field {:name "password" :required true} pass)
  (vali/on-error :email error-item)
  (label "email" "E-mail: ")
  (text-field {:name "email" :required true} email))

(defpartial login [{:keys [user pass]}]
  (vali/on-error :username error-item)
  (label "username" "Username: ")
  (text-field "username" user)
  (vali/on-error :password error-item)
  (label "password" "Password: ")
  (text-field "password" pass))

(defpartial get-new-task [{:keys [task date]}]
  (include-js "http://code.jquery.com/jquery-1.7.2.min.js"
                "http://code.jquery.com/ui/1.8.21/jquery-ui.min.js"
                "/js/jQuery-Timepicker-Addon/jquery-ui-timepicker-addon.js"
                "/js/jQuery-Timepicker-Addon/jquery-ui-sliderAccess.js")
  (include-css "http://code.jquery.com/ui/1.8.21/themes/ui-lightness/jquery-ui.css"
                 "/js/jQuery-Timepicker-Addon.css")
  (label "task" "Task: ")
  (text-area {:name "task" :required true} task)
  (label "date" "Date: ")
  (text-field {:name "date" :require true :id "getdate"} date)
  (include-js "/js/jQuery-Timepicker-Addon/getdate.datetimepicker.js")) ;;js to get the script for the nice gui