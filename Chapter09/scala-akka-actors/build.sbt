name := "scala-akka-actors"

version := "1.0"

scalaVersion := "2.11.8"

val akkaVersion = "2.4.9"

scalaVersion in ThisBuild := "2.11.8"

resolvers += "Akka Repo" at "http://repo.akka.io/releases" 
resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "Sonatype Repo" at "https://oss.sonatype.org/content/repositories/releases/"
resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "Twitter Repo" at "http://maven.twttr.com/"

libraryDependencies += "com.typesafe.akka" %% "akka-actor"   % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-kernel"  % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-remote"  % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-contrib" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % akkaVersion
libraryDependencies += "org.iq80.leveldb" % "leveldb" % "0.7"
libraryDependencies += "org.iq80.leveldb" % "leveldb-api" % "0.7"
libraryDependencies += "org.fusesource.leveldbjni" % "leveldbjni" % "1.8"
libraryDependencies += "org.fusesource.leveldbjni" % "leveldbjni-linux64" % "1.8"
libraryDependencies += "org.fusesource" % "sigar" % "1.6.4"
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.6"

