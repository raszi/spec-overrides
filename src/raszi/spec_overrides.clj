(ns raszi.spec-overrides
  (:require
   [clojure.spec.alpha :as s]
   [clojure.test.check.generators :as gen]))

(def lower-cased #{"foo" "bar" "foobar"})

(def upper-cased #{"FOO" "BAR" "FOOBAR"})

(def spec-test (s/with-gen string?
                 #(gen/elements lower-cased)))

(s/def ::test spec-test)

(s/def ::alias spec-test)

(s/def ::other-alias ::test)

(s/def ::map (s/keys :req-un [::test
                              ::alias
                              ::other-alias]))

(comment
  (gen/generate (s/gen ::test))
  (gen/generate (s/gen ::map))
  (gen/generate (s/gen ::map {::test #(gen/elements upper-cased)})))
