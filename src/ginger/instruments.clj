(ns ginger.instruments
  (:use [overtone.live]))

;; TODO make load path configurable
(def directory (clojure.java.io/file "/home/kelly/.ginger/instruments"))
(def files (file-seq directory))
(def instrument-files
  (filter (fn [^java.io.File file]
            (.isFile file)) files))

(def load-instrument-files
  (apply (fn [^java.io.File file]
           (load-file (.getAbsolutePath file))) instrument-files))

;; TODO Fix so that instruments don't auto play after loading them :P
(load-instrument-files)

(ginger.instruments.foobar/foobar)
(kill ginger.instruments.foobar/foobar)