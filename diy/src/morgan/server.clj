(ns morgan.server
  (:require [noir.server :as server]
            [monger.core :as mg]
            [monger.collection :as mc]
            [clojurewerkz.quartzite.scheduler :as qs])
  (:use [morgan.models.schedule :only [job trigger]]))

(server/load-views "src/morgan/views/")

(mg/connect-via-uri! "mongodb://admin:grwZRUTcdpB3@127.13.73.129:27017/morgan")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "OPENSHIFT_INTERNAL_PORT" "8080"))
	host (get (System/getenv) "OPENSHIFT_INTERNAL_IP")]
    (server/start port {:mode mode
                        :ns 'morgan
                        :jetty-options {:host host}}))
  (qs/initialize)
  (qs/start)
  (qs/schedule job trigger))
