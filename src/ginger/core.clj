(ns ginger.core
  (:use overtone.live)
  (:require [monome-serial.core :as mcore]
            [monome-serial.led :as mled]
            [monome-serial.event-handlers :as mevent]
            [overtone.core :as overtone]
            [ginger.instruments :as instruments]))

(ginger.instruments.foobar/foobar)
(kill ginger.instruments.foobar/foobar)

(defonce m (mcore/connect "/dev/ttyUSB0"))

;; TODO import instruments from ~/.ginger/instruments or some such thing
(defsynth tb303 [note 60 wave 1
                 cutoff 100 r 0.9
                 attack 0.101 decay 0 sustain 1 release 0.4
                 env 200 gate 0 vol 0.8]
  (let [freq (midicps note)
        freqs [freq (* 1.01 freq)]
        vol-env (env-gen (adsr attack decay sustain release)
                         (line:kr 1 0 (+ attack decay release))
                         :action FREE)
        fil-env (env-gen (perc))
        fil-cutoff (+ cutoff (* env fil-env))
        waves [(* vol-env (saw freqs))
               (* vol-env [(pulse (first freqs) 0.5) (lf-tri (second freqs))])]]
        (out 0 (* [vol vol] (rlpf (select wave (apply + waves)) fil-cutoff r)))))

(defn play-note [m x y]
  (mled/led-on m x y)
  (tb303 :note (+ (* x 20) 10)))

(defn kill-note [m x y]
  (mled/led-off m x y)
  (kill tb303))

(mevent/on-press m (fn [x y] (play-note m x y)) "*")
(mevent/on-release m (fn [x y] (kill-note m x y)) "*")
