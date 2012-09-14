(ns morgan.views.welcome
  (:require [morgan.views.common :as common]
            [morgan.models.user :as u]
            [noir.validation :as vali]
            [noir.session :as session]
            [morgan.models.task :as t]
            [morgan.models.email :as e])
  (:use [noir.core :only [defpage render pre-route]]
        [noir.response :only [redirect]]
        [hiccup.core :only [html]]
        [hiccup.form-helpers]
        [hiccup.page-helpers]))

(defpage "/welcome" []
         (common/layout
          [:p "Welcome to morgan"]))

(defpage "/greet/:name" {:keys [name]}
  (str "Hi " name ", I'm glad you are here "
       (u/valid-new-user {:username name})))

(defpage "/greet" []
  (str "Hi " (session/get :username) " you are logged in."))

(defpage "/send-email" []
  (let [tasks (t/next-tasks)]
    (println "task to send ---------->" tasks)
    (future (e/remember-email tasks))
    (doall
     (map t/update-task (map :_id tasks))))
  (common/layout
   [:p (str "ok")]))

(defpage "/card" {:as token}
  (common/layout
   (form-to {:id "payment-form"} [:post "/card"]
            (common/layout
             (common/credit-card token)
             (submit-button "pay")))))

(defpage [:post "/card"] {:as token}
  (common/layout
   [:p (str "token: " token)]))

(defpage "/user/register" {:as user-info}
  (common/layout
    (form-to {:id "payment-form"} [:post "/user/register"]
             (common/new-user user-info)
             (common/credit-card user-info)
             (submit-button "register"))))

(defpage [:post "/user/register"] {:as user-info}
  (common/layout [:p (str user-info)]))
