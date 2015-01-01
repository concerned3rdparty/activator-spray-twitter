name := "activator-spray-twitter"

version := "1.0"

scalaVersion in Global := "2.11.4"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)

parallelExecution in Test := false

javaOptions in run += "-javaagent:" + System.getProperty("user.home") + "/.ivy2/cache/org.aspectj/aspectjweaver/jars/aspectjweaver-1.7.3.jar"

fork in run := true

connectInput in run := true

outputStrategy in run := Some(StdoutOutput)

lazy val core = CoreBuild.core

lazy val web = WebBuild.web enablePlugins(PlayScala) dependsOn core
