resolvers += "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("name.heikoseeberger" % "sbt-groll" % "1.6.0")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.7")
