(ns ginger.core
  (:use overtone.live)
  (:require [monome-serial.core :as mcore]
            [monome-serial.led :as mled]
            [monome-serial.event-handlers :as mevent]
            [overtone.core :as overtone]))

(defonce m (mcore/connect "/dev/ttyUSB0"))

(definst foo [freq 440] (saw freq))

(defn play-note [m x y]
  (mled/led-on m x y)
  (foo (+ (* x 50) (* y 150))))

(defn kill-note [m x y]
  (mled/led-off m x y)
  (kill foo))

(mevent/on-press m (fn [x y] (play-note m x y)) "*")
(mevent/on-release m (fn [x y] (kill-note m x y)) "*")
