(ns raszi.spec-overrides-test
  (:require
   [clojure.spec.alpha :as s]
   [clojure.test :refer [deftest is testing]]
   [clojure.test.check.generators :as gen]
   [raszi.spec-overrides :as sut]))

(deftest test-spec
  (is (sut/lower-cased (gen/generate (s/gen ::sut/test)))))

(deftest alias-spec
  (is (sut/lower-cased (gen/generate (s/gen ::sut/test)))))

(deftest other-alias-spec
  (is (sut/lower-cased (gen/generate (s/gen ::sut/test)))))

(deftest overrides
  (testing "without overrides"
    (is (every? sut/lower-cased (vals (gen/generate (s/gen ::sut/map))))))
  (testing "with overrides"
    (let [generated (gen/generate (s/gen ::sut/map {::sut/test #(gen/elements sut/upper-cased)}))]
      (is (sut/upper-cased (:test generated)))
      (is (sut/lower-cased (:other-alias generated)))
      (is (sut/lower-cased (:alias generated))))))
