(ns ginger.core
  (:use [overtone.live])
  (:require [monome-serial.core :as mcore]
            [monome-serial.led :as mled]
            [monome-serial.event-handlers :as mevent]
            [overtone.core :as overtone]
            [ginger.controls :as controls])
  (gen-class))

(defonce m (mcore/connect "/dev/ttyUSB0"))

;; TODO Make configurable
(def PORT 9000)

(def client
  (osc-client "localhost" PORT))

;; TODO Seems uneeded.
;;      Learning clojure woo!
(dissoc controls/instruments)

;; TODO Need to find some way of having instruments define
;;      the notes that they use to play.
(defn play-note [m x y]
  (mled/led-on m x y)
  ((controls/get-instrument (deref controls/current-index)) :note (+ (+ (* x 1) (* y 8)) 30)))

;; TODO Velocity with chronome
(defn play-renoise-note [m x y]
  (osc-send
   client
   "/renoise/trigger/note_on"
   -1
   -1
   (+ (+ (* x 1) (* y 8))30)
   127))

(defn kill-renoise-note [m x y]
  (osc-send
   client
   "/renoise/trigger/note_off"
   -1
   -1
   (+ (+ (* x 1) (* y 8))30)))


(defn kill-note [m x y]
  (mled/led-off m x y)
  (stop))

(defn delegate-on-press [m x y]
  ;; TODO Add in some sort of plugin delegate / only play overtone / only send out osc
  (when (> y 0)
    ;; TODO Renable when done with renoise
    ;; (play-note m x y))
    (play-renoise-note m x y))
  (when (= y 0)
    (controls/handle-press-event m x y)))

(defn delegate-on-release [m x y]
  (when (> y 0)
    ;; TODO Renable when done with renoise
    ;;(kill-note m x y)))
    (kill-renoise-note)))

(mevent/on-press m (fn [x y] (delegate-on-press m x y)) "*")
(mevent/on-release m (fn [x y] (delegate-on-release m x y)) "*")