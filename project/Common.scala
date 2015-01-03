import sbt._
import Keys._

object BuildSettings {
  val common = Seq(
    resolvers ++= Seq(
      Resolver.sonatypeRepo("snapshots"),
      "spray repo" at "http://repo.spray.io"
    )
  )
}


object Dependencies {

  val specs2 = "org.specs2" %% "specs2" % "2.3.11" % "test"
  
  object WebJars {
    val angularJs = "org.webjars" % "angularjs" % "1.3.8"
    val requireJs = "org.webjars" % "requirejs" % "2.1.11-1"
    val bootstrap = "org.webjars" % "bootstrap" % "3.0.2"
    val underscoreJs = "org.webjars" % "underscorejs" % "1.7.0-1"
    val angularnvd3Directives = "org.webjars" % "angularjs-nvd3-directives" % "0.0.7-1"
  }

  object Akka {
    private val version = "2.3.6"
    private def akka(name: String) =
      "com.typesafe.akka" %% s"akka-$name" % version

    val testkit = akka("testkit") % "test;provided"
    val actor = akka("actor") % "provided"
    val slf4j = akka("slf4j") % "provided"
  }

  object Spray {
    private val defaultVersion = "1.3.2"
    private val base = "io.spray"
    private def spray(name: String) =
      base %% s"spray-$name" % defaultVersion

    val can = spray("can")
    val client = spray("client")
    val routing = spray("routing")
    val json = base %% "spray-json" % "1.3.1"
    val testkit = spray("testkit") % "test"
  }

}
