name := """ReactiveWebStore"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  //jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.typesafe.akka" %% "akka-testkit" % "2.4.4" % Test,
  "com.netflix.rxjava" % "rxjava-scala" % "0.20.7",
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "mysql" % "mysql-connector-java" % "6.0.3",
  "net.sf.jasperreports" % "jasperreports" % "6.2.2"  withSources() ,
  "net.sf.jasperreports" % "jasperreports-functions" % "6.2.2",
  "net.sf.jasperreports" % "jasperreports-chart-themes" % "6.2.2",
  "io.swagger" %% "swagger-play2" % "1.5.2-SNAPSHOT"
)

resolvers += "Jasper" at "https://jaspersoft.artifactoryonline.com/jaspersoft/repo/"
resolvers += "JasperSoft" at "https://jaspersoft.artifactoryonline.com/jaspersoft/jaspersoft-repo/"
resolvers += "Jasper3rd" at "https://jaspersoft.artifactoryonline.com/jaspersoft/jaspersoft-3rd-party/"
resolvers += "mondrian-repo-cache" at "https://jaspersoft.artifactoryonline.com/jaspersoft/mondrian-repo-cache/"
resolvers += "spring-mil" at "http://repo.spring.io/libs-milestone"
resolvers += "spring-rel" at "http://repo.spring.io/libs-release"
resolvers += "oss" at "https://oss.sonatype.org/content/groups/public/"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += DefaultMavenRepository
