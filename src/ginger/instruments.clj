(ns ginger.instruments
  (:use [overtone.live])
  (gen-class))

;; TODO make load path configurable
(def directory (clojure.java.io/file "/home/kelly/.ginger/instruments"))

(def files (file-seq directory))

(def instrument-files
  (filter
   (fn [^java.io.File file]
     (.isFile file)) files))

(def load-instrument-files
  (for [^java.io.File instrument-file instrument-files]
    (load-file
     (.getAbsolutePath instrument-file))))