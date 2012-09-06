(ns ginger.controls
  (:use [overtone.live])
  (:require [ginger.instruments]
            [monome-serial.led :as mled])
  (gen-class))

(defonce instruments ginger.instruments/load-instrument-files)

(dissoc instruments)

(def current-index (atom 0))
(def recording-status (atom false))
(def samples (atom {}))

(defn get-instrument [index]
  (nth instruments index))

;; TODO Refactor
(defn switch-instrument-index [m x y]
  (when (= x 7)
    (swap! current-index inc)
    (mled/led-on m x y)
    (mled/led-off m 6 y))
  (when (= x 6)
    (swap! current-index dec)
    (mled/led-on m x y)
    (mled/led-off m 7 y)))

(import '(java.io RandomAccessFile))

;; TODO Refactor
(defn switch-recording [m x y]
  (swap! recording-status not)
  (when (deref recording-status)
    (recording-start)
    (mled/led-on m x y))
  (when (not (deref recording-status))
    (recording-stop)
    (mled/led-off m x y)))

(defn handle-press-event [m x y]
  (when (>= x 6)
    (switch-instrument-index m x y))
  (when (and (>= x 4) (< x 6))
    (switch-recording m x y)))