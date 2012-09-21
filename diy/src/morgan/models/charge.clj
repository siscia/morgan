(ns morgan.models.charge
  (:require [clj-stripe.common :as common]
            [clj-stripe.plans :as plans]
            [clj-stripe.customers :as customers]))

(defn start-charge-basic-plan [token email]
  (common/with-token token
    (common/execute
     (customers/create-customer (common/card token)
                                (common/plan "morgan-basic")
                                (customers/email email)))))
