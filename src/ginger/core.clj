(ns ginger.core
  (:use overtone.live)
  (:require [monome-serial.core :as mcore]
            [monome-serial.led :as mled]
            [monome-serial.event-handlers :as mevent]
            [overtone.core :as overtone]
            [ginger.instruments]))

(defonce instruments ginger.instruments/load-instrument-files)
(defonce m (mcore/connect "/dev/ttyUSB0"))

(dissoc instruments)

;; TODO load first instrument to monome
;; TODO allow users to switch between instruments

;; TODO allow notes to play scales defined by the instrument
(defn play-note [m x y]
  (mled/led-on m x y)
  (beep :note (+ (+ (* x 1) (* y 8)) 20)))

(defn kill-note [m x y]
  (mled/led-off m x y)
  (beep overpad))

(mevent/on-press m (fn [x y] (play-note m x y)) "*")
(mevent/on-release m (fn [x y] (kill-note m x y)) "*")