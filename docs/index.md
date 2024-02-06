## koka-shokki

This library is in a WORK IN PROGRESS state, it could potentially contain horrible bugs.

### Usage

This library is currently available for Scala binary versions 2.13 and 3.1.

To use the latest version, include the following in your `build.sbt`:

```scala
libraryDependencies ++= Seq(
  "io.mangomesh" %% "koka-shokki" % "@VERSION@"
)
```

to execute this library, you also require the following JVM argument:

```
--add-exports","java.base/jdk.internal.vm=ALL-UNNAMED"
```

### Code

An example could be this:

```scala
def divide(
      x: With[Optional, Int],
      y: With[Optional, Int]
  ): Int <~ Optional :| Lefted[String] =
    WithCapability[Optional :| Lefted[String], Int] {
      val result: With[Lefted[String], Int] =
        if (y.? == 0) asLeft("division by zero") else ok(x.? / y.?)
      result.?
    }
```

which could be use like this:

```scala
def runDivEffect(
      x: With[Optional :| Lefted[String], Int]
  ): Either[String, Int] = {
    runEffect(x) {
      case Left(channel) =>
        channel match {
          case _: Optional[Int] => Left("The value is not present")
          case error: Lefted[String][Int] =>
            Left(error.err)
        }
      case Right(value) => Right(value)
    }
  }

val x = divide(Some(1), Some(0))
val result = runDivEffect(x)
```

note that we require to provide an evaluator for the channels.