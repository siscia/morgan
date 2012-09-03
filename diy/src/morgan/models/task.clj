(ns morgan.models.task
  (:require [monger.collection :as mc]
            [clj-time.core :as t])
  (:use [clj-time.format :only [formatter parse]]
        [monger.joda-time]
        [monger.operators]))

;; 22-08-2012 00:00 +0200

(def ^:dynamic *time-swap*
  (t/plus (t/now) (t/hours 1)))

(defn read-time [time]
  (let [c (formatter "dd-MM-yyyy HH:mm Z")
        date (parse c time)]
     date))

(defn memorize-task [task time username]
  (let [time (read-time time)
        id-user (:_id (mc/find-one-as-map "user" {:username username}))
        date-remember (sort (filter #(t/before? (t/now) %) (map #(t/minus time %) [(t/months 3) (t/months 1) (t/weeks 2) (t/weeks 1) (t/days 5) (t/days 2) (t/days 1)])))]
    (println task (class task))
    (mc/insert "task" {:task task
                       :time time
                       :id_user id-user
                       :next_remember (first date-remember)
                       :date_remember (vec (next date-remember))})))

(defn next-tasks [& {:keys [time-swap] :or {time-swap *time-swap*}}]
  (mc/find-maps "task" {:next_remember {$gte (t/now)
                                        $lt time-swap}}))

(defn update-task [oid]
  (let [next-remember (-> (mc/find-map-by-id "task" oid) :date_remember first)]
    (mc/update-by-id "task" oid { $pop {:date_remember -1}
                                  $set {:next_remember next-remember}})))