name := "slick-pg-issue"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-lang3" % "3.3.2",
  "com.github.tminglei" %% "slick-pg" % "0.9.0-beta",
  "org.joda" % "joda-convert" % "1.7",
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "com.typesafe.play" %% "play-json" % "2.3.8",
  "com.vividsolutions" % "jts" % "1.13"
)