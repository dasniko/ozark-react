# Isomorphic (Universal) Ozark ReactJS Demo

[![Build Status](https://img.shields.io/travis/dasniko/ozark-react-example.svg)](https://travis-ci.org/dasniko/ozark-react-example)

This is a simple demo application demonstrating how to use isomorphic/universal JavaScript with server-side pre-rendered [ReactJS](http://www.reactjs.org) templates with the new Java EE MVC 1.0 ([JSR-371](https://jcp.org/en/jsr/detail?id=371)) reference implementation [Ozark](https://ozark.java.net).

This demo makes use of the great **[ozark-react](https://github.com/dasniko/ozark-react)** ViewEngine for Ozark/MVC 1.0.

This application is inspired by the following demos:

- [TODO MVC](https://github.com/chkal/todo-mvc) by [@chkal](https://twitter.com/chkal), a simple TODO app using MVC 1.0/Ozark
- [Isomorphic Spring Boot React.js Example](https://github.com/winterbe/spring-react-example) by [@winterbe_](https://twitter.com/winterbe_)
- [React.js Tutorial](http://facebook.github.io/react/docs/tutorial.html)


## Basics

MVC 1.0 is at a very early stage and this project uses snapshot versions of Ozark.
So there might be some not yet known effects/troubles when running the application.

For your convenience, this project comes with all dependencies which are needed to run the application in a pure servlet-container like [Apache Tomcat](http://tomcat.apache.org) or [Jetty](http://eclipse.org/jetty/).
So you don't have to rely on your container to provide all of the technology and libraries
(Ozark itself is yet developed for [Glassfish](https://glassfish.java.net) only, but this might (hopefully) change in the future).


## Getting Started

This project is Gradle based and comes with a Gradle Wrapper, so you don't have to have Gradle installed on your machine.
To build the WAR file, just type

    $ ./gradlew build

in your console/terminal. This will build a `ozark-react-example.war` in the `build/libs` folder. You can deploy this WAR into an [Apache Tomcat](http://tomcat.apache.org) or [Jetty](http://eclipse.org/jetty/) servlet container.
After starting your server, you should be able to access the application at this address:

    http://localhost:8080/ozark-react/


## Contribution / Issues

Don't hesitate to contribute to this project in any kind of sending [PRs](https://github.com/dasniko/ozark-react-example/pulls) for improvements, enhancements, and/or bug-fixes.

I appreciate every issue that will be [reported](https://github.com/dasniko/ozark-react-example/issues).


## License

- [MIT License](https://github.com/dasniko/ozark-react-example/blob/master/LICENSE)
