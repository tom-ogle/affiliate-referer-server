name := """referer-server"""

version := "1.0-SNAPSHOT"
import play.sbt.routes.RoutesKeys.routesImport
routesImport += "binders.ProjectBinders._"

lazy val root = (project in file(".")).enablePlugins(PlayScala)


scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
    jdbc,
    cache,
    ws,
"org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
