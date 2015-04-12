# Sbt dependency

```scala
libraryDependencies += "io.github.morgaroth" %% "utils-akka" % "1.3.0"
```


# CHANGELOG

* v1.3.0:
     * added WarnUnhandled trait, which enable easy logging of unhandled messages in actor 
     * added implicit LoggingAdapter to make logger from ActorLogging implicit and be passable via implicit arguments to functions
     * init
