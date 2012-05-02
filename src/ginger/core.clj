(ns ginger.core
  (:use overtone.live)
  (:require [monome-serial.core :as mcore]
            [monome-serial.led :as mled]
            [monome-serial.event-handlers :as mevent]
            [overtone.core :as overtone]
            [ginger.instruments])
  (gen-class))

(defonce instruments ginger.instruments/load-instrument-files)
(defonce m (mcore/connect "/dev/ttyUSB0"))

(intern 'ginger.core 'instrument-index 0)
(dissoc instruments)

;; TODO allow users to switch between instruments
(def current-instrument
  (nth instruments instrument-index))

(defn play-note [m x y]
  (mled/led-on m x y)
  (current-instrument :note (+ (+ (* x 1) (* y 8)) 20)))

(defn kill-note [m x y]
  (mled/led-off m x y)
  (kill current-instrument))

(defn delegate-on-press [m x y]
  (when (> y 0)
    (play-note m x y)))

(defn delegate-on-release [m x y]
  (when (> y 0)
    (kill-note m x y)))

;; TODO allow notes to play scales defined by the instrument
(mevent/on-press m (fn [x y] (delegate-on-press m x y)) "*")
(mevent/on-release m (fn [x y] (delegate-on-release m x y)) "*")