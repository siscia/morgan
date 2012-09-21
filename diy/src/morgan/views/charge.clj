(ns morgan.views.charge
  (:require [net.cgrand.enlive-html :as html]
            [morgan.models.charge :as charge])
  (:use [morgan.views.layout :only [layout]]
        [noir.core]))


(def js-script ["https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"
                "https://js.stripe.com/v1/"
                "/js/stripe/get-payments.js"])

(html/defsnippet charge "morgan/views/charge.html" [:#register]
  [_])

(defpage "/charge" []
  (layout
   {:script-js js-script
    :wrapper (charge nil)}))

(defpage [:post "/charge"] {:keys [username password email stripeToken] :as new-user}
  (charge/start-charge-basic-plan stripeToken email)
  (println stripeToken)) 