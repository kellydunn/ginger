```
       __
   __ /\_\    ___      __      __  _ __
 /'_ `\/\ \ /' _ `\  /'_ `\  /'__`\\`'__\
/\ \L\ \ \ \/\ \/\ \/\ \L\ \/\  __/ \ \/
\ \____ \ \_\ \_\ \_\ \____ \ \____\ \_\
 \/___L\ \/_/\/_/\/_/\/___L\ \/____/\/_/
   /\____/             /\____/
   \_/__/              \_/__/

```

# what

A monome synthesizer using overtone + the clojure monome-serial library.

# why

I want to be able to download overtone instruments and apply them directly to my monome / have the ability to change them on the fly.

# install

Install the necessary clojure dependencies

```
cake deps
```

Now make yourself a directory of instruments:

```
mkdir -p ~/.ginger/instruments
```

Now you can start adding in `.clj` files that describe overtone insturments / synths in this directory.

The current source code assumes that you have a `beep.clj` instrument defined in this configuration directory.

# usage

- boot up swank-server ala slime in an emacs buffer: `M-x clojure-jack-in <RET>`
- in your slime REPL, enter in: `(use 'ginger.core)`
- start mashing buttons

# TODOs

- allow user to toggle through instruments on the monome (currently you can only toggle through the first two)
- determine pitch mapping from instruments
- dynamic home path
- autoload ttyUSB / configure loading monome
