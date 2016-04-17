# Isomorphic (Universal) Ozark ReactJS ViewEngine Demo

[![Release](https://img.shields.io/github/release/dasniko/ozark-react.svg)](https://jitpack.io/#dasniko/ozark-react)
[![Build Status](https://img.shields.io/travis/dasniko/ozark-react-example.svg)](https://travis-ci.org/dasniko/ozark-react-example)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/dasniko/ozark-react-example/blob/master/LICENSE)

This is a simple demo application demonstrating how to use isomorphic/universal JavaScript with server-side pre-rendered [ReactJS](http://www.reactjs.org) templates with the new Java EE MVC 1.0 ([JSR-371](https://jcp.org/en/jsr/detail?id=371)) reference implementation [Ozark](https://ozark.java.net).

To establish the use of React.JS on the Java server-side, I created a `ViewEngine` implementation for Ozark/MVC 1.0, which uses the server-side rendering option of React.JS.

## Basics

MVC 1.0 is at a very early stage and this project uses snapshot versions of Ozark.
So there might be some not yet known effects/troubles when running the application.

- [MVC 1.0](https://java.net/projects/mvc-spec/pages/Home) specification / [JSR-371](https://www.jcp.org/en/jsr/detail?id=371)
- [Ozark reference implementation](https://ozark.java.net/)

For your convenience, this project comes with all dependencies which are needed to run the application in a pure servlet-container like [Apache Tomcat](http://tomcat.apache.org) or [Jetty](http://eclipse.org/jetty/).
So you don't have to rely on your container to provide all of the technology and libraries
(Ozark itself is yet developed for [Glassfish](https://glassfish.java.net) only, but this might (hopefully) change in the future).


## Getting Started

This project is Gradle based and comes with a Gradle Wrapper, so you don't have to have Gradle installed on your machine.
To build the WAR file, just type

    $ ./gradlew build

in your console/terminal. This will build a `ozark-react.war` in the `build/libs` folder.

The project comes with all mechanisms to install and bundle all dependencies.
Not only the Java deps, but also the NPM JavaScript dependencies like React.JS (the actual runtime lib), Webpack and Babel (for transpiling and bundling)

You can deploy this WAR into an [Apache Tomcat](http://tomcat.apache.org) or [Jetty](http://eclipse.org/jetty/) servlet container.
After starting your server, you should be able to access the application at this address:

[http://localhost:8080/ozark/react](http://localhost:8080/ozark/react)

_BTW: You don't have to use Gradle to use it in your project, just use whatever build tool you like (e.g. Maven, SBT, etc.)_

## Building the JavaScript artifacts

I worked a lot on building, bundling and packaging (frontend) JavaScript artifacts with toolks like Maven and Gradle, which are not really meant for JavaScript (although it is possible, somehow).
But after all, I came (again) to the end, that you should _use the right tool for the right job._
In case of JavaScript artifacts, this means you should definetely use tools from this ecosystem, like NPM, Webpack, Babel, Grunt, Gulp, and/or whatever.

For this reason, I use Webpack with integrated Babel.js to transpile the JSX/ES6 components into executable JS (ES5) code and bundle it together with React.JS into one app file.
This file is then used within the application at runtime (server- and client-side).

Also, if this means that you have two different build tools (like Gradle and NPM here) for one project, the configuration and result of the tools is much better than trying something else.
Luckily, it's easily possible, to start the NPM tasks from within the Gradle build process, so it won't become pain in the ass to run both when needed.

## Contribution / Issues

Don't hesitate to contribute to this project in any kind of sending [PRs](https://github.com/dasniko/ozark-react/pulls) for improvements, enhancements, and/or bug-fixes.

I appreciate every issue that will be [reported](https://github.com/dasniko/ozark-react/issues).
