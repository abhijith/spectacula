(ns spectacula.core
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.spec.test.alpha :as stest]))

(s/conform even? 10)
(s/valid? even? 10)
(s/valid? nil? nil)

(import 'java.util.Date)

(s/valid? inst? (Date.))

(s/valid? #{:club :diamond :heart :spade} :club) ;; true

(s/valid? [5 4 6] 2)
(s/valid? {:a 1 :b 2 :c 3} :a)

(s/def ::vec [4 5 6])

(s/def ::date inst?)
(s/def ::suit #{:club :diamond :spade :heart})

(s/valid? ::date (new Date))
(s/valid? ::suit :club)
(s/valid? ::vec 2)

(clojure.repl/doc ::date)
(clojure.repl/doc ::suit)

(s/def ::big-even (s/and int? even? #(> % 1000)))
(s/valid? ::big-even 10)
(s/valid? ::big-even 1001)
(s/valid? ::big-even 1002)

(s/def ::name-or-id (s/or :name string? :id int?))
(clojure.repl/doc ::name-or-id)
(s/valid? ::name-or-id 100)
(s/valid? ::name-or-id "asdf")
(s/valid? ::name-or-id :asdf)

(s/conform ::name-or-id "asdf")
(s/conform ::name-or-id 1)

(s/valid? string? nil)

                                        ; treat nil as valid value
(s/valid? (s/nilable string?) "")
(s/valid? (s/nilable string?) nil)

(s/explain ::suit 42)
(s/explain ::name-or-id :foo)
(s/explain ::name-or-id "a")

(s/explain-data ::name-or-id :foo)
(s/explain-str ::name-or-id :foo)

(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")
(s/def ::email-type (s/and string? #(re-matches email-regex %)))

(s/def ::first-name string?)
(s/def ::last-name string?)
(s/def ::email ::email-type)

(s/def ::person (s/keys :req [::first-name ::last-name ::email]
                        :opt [::phone]))

(s/valid? ::person
          {::first-name "Elon"
           ::last-name "Musk"
           ::email "elon@example.com"})

;; Fails required key check
(s/explain ::person {::first-name "elon"})

;; Fails attribute conformance
(s/explain ::person
           {::first-name "Elon"
            ::last-name "Musk"
            ::email "n/a"})

(s/conform (s/coll-of keyword?) #{:a :b :c})

(s/conform (s/coll-of keyword? :kind vector? :count 3 :distinct true) [:a :b :c])

(s/conform (s/coll-of keyword? :kind vector? :min-count 1 :max-count 2 :distinct true) [:a :b :c])

;; positional
(s/def ::point (s/tuple integer? integer?))
(s/conform ::point [1 2])
(s/conform ::point '(1 2))

;; invalid - set has no ordering
(s/conform ::point #{1 2})

(gen/generate (s/gen int?))
(gen/generate (s/gen (s/int-in 1 6)))

(s/def ::roll (s/int-in 0 11))
(gen/sample (s/gen ::roll))
