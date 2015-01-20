import sbt._
import Keys._

object WebBuild {
  import Dependencies._

  val settings = BuildSettings.common ++ Seq(
    libraryDependencies ++= Seq(
      WebJars.requireJs,
      WebJars.bootstrap,
      WebJars.underscoreJs,
      WebJars.angularJs
    )
  )
  val web = project in file("web") settings (settings: _*)
}
