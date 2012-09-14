(ns morgan.views.task
  (:require [net.cgrand.enlive-html :as html]
            [morgan.models.task :as t]
            [noir.session :as session])
  (:use [noir.core :only [defpage pre-route]]
        [noir.response :only [redirect]])) 

(html/deftemplate task  "morgan/views/task.html"
  [tasks]
  [:#tasks]
  (html/clone-for [i tasks]
                  [:tr :td.task]
                  (html/content (:task i))

                  [:tr :td.date]
                  (html/content (str (:time i)))))

(pre-route "/task" {}
          (when-not (session/get :user-id)
            (redirect "/login")))

(defpage "/task" []
  (task (t/task-from-user-id (session/get :user-id))))

(defpage [:post "/task"] {:keys [new-task date]} 
  (let [user-id (session/get :user-id)]
    (t/memorize-task new-task date user-id)
    (task (t/task-from-user-id user-id))))

