(ns ginger.instruments
  (:use [overtone.live]))

(import [ginger.instruments.foobar])

(ginger.instruments.foobar/foobar)
(kill ginger.instruments.foobar/foobar)