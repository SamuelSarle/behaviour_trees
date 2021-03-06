(ns behaviour-trees.core
  (:refer-clojure :exclude [sequence]))

(defn exec
  ([] true)
  ([x] (eval x)))

(defmacro randomized-order [f & args]
   `(~f #(identity (exec %)) (shuffle '(~@args))))

(defmacro sequence
  ([x] x)
  ([x & rest]
   `(and ~x ~@rest)))

(defmacro rand-sequence
  ([x] x)
  ([x & rest]
   `(randomized-order every? ~x ~@rest)))

(defmacro selector
  ([x] x)
  ([x & rest]
   `(or ~x ~@rest)))

(defmacro rand-selector
  ([x] x)
  ([x & rest]
   `(randomized-order some ~x ~@rest)))

(defmacro inverter [x]
  `(not ~x))

(defmacro succeeder [x]
  `(do
     ~x
     true))

(defmacro failer [x]
  `(inverter (succeeder ~x)))

(defmacro until [pred]
  `(loop []
     (if ~pred
       true
       (recur))))

(defmacro until-success [x]
  `(until (true? ~x)))

(defmacro until-failure [x]
  `(until (inverter (true? ~x))))


