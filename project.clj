(defproject spectacula "0.1.0-SNAPSHOT"
  :plugins [[cider/cider-nrepl "0.17.0"]
            [lein-cljfmt "0.5.7"]]
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/test.check "0.9.0"]]}})