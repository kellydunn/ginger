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

A Monome synthesizer using overtone + the clojure monome-serial library.

# why

I wannt to be able to download overtone instruments and apply them directly to my monome / have the ability to change them on the fly.

# install

```
cake deps
```

# usage

- boot up slime in an emacs buffer
- edit the path to your monome's current connection in core.clj
- start mashing buttons

# TODOs

- instrument loading
- determine pitch mapping from external source
- autoload ttyUSB / configure loading monome
