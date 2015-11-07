# Ozark ReactJS ViewEngine

[![Release](https://img.shields.io/github/release/dasniko/ozark-react.svg)](https://jitpack.io/#dasniko/ozark-react)
[![Build Status](https://img.shields.io/travis/dasniko/ozark-react.svg)](https://travis-ci.org/dasniko/ozark-react)

This is a ViewEngine implementation for Ozark/MVC 1.0 which uses [ReactJS](http://www.reactjs.org) as the templating mechanism (and technically additionally the built-in JSP engine).

With this ViewEngine, it is possible do create isomorphic/universal JavaScript applications with Java EE (8), in which Single-Page-Apps will be pre-rendered on the server (good for SEO and response time), delivered to the client(s) and continue working there with the same code.


## Demo

A working demo/example with this ViewEngine can be found in the project **[ozark-react-example](https://github.com/dasniko/ozark-react-example)**


## MVC/Ozark Basics

MVC 1.0 is at a very early stage and this project uses snapshot versions of Ozark.
So there might be some not yet known effects/troubles when running the code.

- [MVC 1.0](https://java.net/projects/mvc-spec/pages/Home) specification / [JSR-371](https://www.jcp.org/en/jsr/detail?id=371)
- [Ozark reference implementation](https://ozark.java.net/)


## Usage / Getting Started

To be able to use this library as a dependency in your own project, check it out and build it with (the provided) Gradle(wrapper):

    $ ./gradlew install

If you don't want to build it on your own, you can use a ready build binary from [Jitpack.io](https://jitpack.io).
A detailed description can be found at

- [https://jitpack.io/#dasniko/ozark-react/0.1.0](https://jitpack.io/#dasniko/ozark-react/0.1.0)  
_(possibly you have to change the version tag to the correct version)_

You don't have to use Gradle to use it in your project, just use whatever build tool you like (e.g. Maven, SBT, etc.)


## Contribution / Issues

Don't hesitate to contribute to this project in any kind of sending [PRs](https://github.com/dasniko/ozark-react/pulls) for improvements, enhancements, and/or bug-fixes.

I appreciate every issue that will be [reported](https://github.com/dasniko/ozark-react/issues).


## License

- [MIT License](https://github.com/dasniko/ozark-react/blob/master/LICENSE)
