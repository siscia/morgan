(ns morgan.models.schedule
  (:require [clojurewerkz.quartzite.scheduler :as qs]
            [clojurewerkz.quartzite.jobs :as j]
            [clojurewerkz.quartzite.triggers :as t]
            [clojurewerkz.quartzite.schedule.simple :as s]

            [morgan.models.task]
            [morgan.models.email]))

(j/defjob BasicEmail [ctx]
  (let [tasks (morgan.models.task/next-tasks)]
    (future (morgan.models.email/remember-email tasks))
    (doall
     (map morgan.models.task/update-task (map :_id tasks)))))

(def job (j/build
             (j/of-type BasicEmail)
             (j/with-identity (j/key "trivialprint.1"))))

(def trigger (t/build
                 (t/with-identity (t/key "trigger.1"))
                 (t/start-now)
                 (t/with-schedule (s/schedule
                                   (s/repeat-forever)
                                   (s/with-interval-in-minutes 3)))))