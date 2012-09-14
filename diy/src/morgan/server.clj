(ns morgan.server
  (:require [noir.server :as server]
            [morgan.models :as models]
            [clojurewerkz.quartzite.scheduler :as qs])
  (:use [morgan.models.schedule :only [job trigger]]))

(server/load-views "src/morgan/views")
(server/load-views "src/morgan/noir_auth_app/views")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "OPENSHIFT_INTERNAL_PORT" "8080"))
	host (get (System/getenv) "OPENSHIFT_INTERNAL_IP")]
    (server/start port {:mode mode
                        :ns 'morgan
                        :jetty-options {:host host}}))

  (models/maybe-init)
  
  (qs/initialize)
  (qs/start)
  (qs/schedule job trigger))
