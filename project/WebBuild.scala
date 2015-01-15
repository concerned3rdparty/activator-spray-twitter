import sbt._
import Keys._

object WebBuild {
  import Dependencies._

  val settings = BuildSettings.common ++ Seq(
    libraryDependencies ++= Seq(
      WebJars.requireJs,
      WebJars.bootstrap,
      WebJars.underscoreJs,
      WebJars.angularJs,
      WebJars.angularnvd3
    )
  )
  val web = project in file("web") settings (settings: _*)
}
