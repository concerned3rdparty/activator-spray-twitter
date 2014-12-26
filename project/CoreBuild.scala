import sbt._
import Keys._

object CoreBuild {
  import Dependencies._

  val settings = BuildSettings.common ++ Seq(
    libraryDependencies ++= Seq(
      Akka.actor,
      Akka.slf4j,
      Spray.can,
      Spray.client,
      Spray.routing,
      Spray.json,
      specs2,
      Spray.testkit,
      Akka.testkit
    )
  )
  val core = project in file("core") settings (settings: _*)
}
