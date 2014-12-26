name := "activator-spray-twitter"

version := "1.0"

scalaVersion := "2.10.4"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "spray nightlies repo" at "http://nightlies.spray.io"

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
  "com.typesafe.akka"      %% "akka-actor"            % "2.3.6",
  "com.typesafe.akka"      %% "akka-slf4j"            % "2.3.6",
  "io.spray"                % "spray-client"          % "1.3.2-20140909",
  "io.spray"               %% "spray-json"            % "1.3.2-20140909",
  "com.wandoulabs.akka"    %% "spray-websocket"       % "0.1.3",
  "org.eigengo.monitor"     % "agent-akka"            % "0.2-SNAPSHOT",
  "org.eigengo.monitor"     % "output-statsd"         % "0.2-SNAPSHOT"
)

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
