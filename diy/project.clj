(defproject morgan "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :plugins [[lein-cljsbuild "0.2.1"]]

			:cljsbuild {
                :builds [{
                    ; The path to the top-level ClojureScript source directory:
                    :source-path "src"  ; "src-cljs"
                    ; The standard ClojureScript compiler options:
                    ; (See the ClojureScript compiler documentation for details.)
                    ;
                    ; For jayq (see https://github.com/ibdknox/jayq )...
                    ; If you're using advanced Clojurescript compilation 
                    ; you'll need to reference the jquery externs file. Add
                    ; this to your compilation options:
                    ; {
                    ;   :optimizations :advanced
                    ;   :externs ["externs/jquery.js"]
                    ;   ...
                    ; }
                    ;
                    ; More on :optimizations :whitespace and :externs in
                    ; http://lukevanderhart.com/2011/09/30/using-javascript-and-clojurescript.html
                    :compiler {
                      :output-to "resources/public/js/cljs.js"  ; default: main.js in current directory
                      
                      ; ; development
                      ; :optimizations :whitespace
                      ; :pretty-print true

                      ; production
                      :optimizations :advanced
                      :pretty-print false
                      :externs ["externs/jquery.js"]

                    }}]}
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [noir "1.2.2"]
			   [com.novemberain/monger "1.2.0"]
                           [clj-time "0.4.3"]
                           [org.clojure/data.json "0.1.2"]
                           [com.draines/postal "1.8.0"]
                           [clojurewerkz/quartzite "1.0.0-rc6"]
                           [org.clojars.siscia/clj-stripe "1.0.3"]
                           [jayq "0.1.0-alpha4"]
                           [fetch "0.1.0-alpha2"]
                           [enlive "1.0.1"]]
            :dev-dependencies [[swank-clojure "1.4.0-SNAPSHOT"]
                               [lein-checkouts "1.0.0"]]
            :main morgan.server)
