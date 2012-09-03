(defproject morgan "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [noir "1.2.1"]
			   [com.novemberain/monger "1.1.2"]
                           [clj-time "0.4.3"]
                           [org.clojure/data.json "0.1.2"]
                           [com.draines/postal "1.8.0"]
                           [clojurewerkz/quartzite "1.0.0-rc6"]
                           [abengoa/clj-stripe "1.0.0"]]
            :dev-dependencies [[swank-clojure "1.4.0-SNAPSHOT"]]
            :main morgan.server)
