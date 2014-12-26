import sbt._
import Keys._

object Build extends Build {
  lazy val root =
    Project(id = "activator-spray-twitter", base = file("."))
    .dependsOn(uri("https://github.com/lihaoyi/SprayWebSockets.git"))
}
