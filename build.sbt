import SbtReleaseHelpers._
import sbtbuildinfo.Plugin._
import sbtrelease.ReleasePlugin._
import sbtrelease.ReleaseStateTransformations._
import sbtrelease.ReleaseStep
import sbtrelease.ReleasePlugin.ReleaseKeys.crossBuild

name := """utils-akka"""

scalacOptions ++= Seq("-feature")

organization := """io.github.morgaroth"""

crossScalaVersions := Seq("2.10.4", "2.11.6")

resolvers ++= Seq(
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"
)

val ficusDependency = scalaVersion {
  case ver_2_11 if  ver_2_11 startsWith "2.11." =>
    "net.ceedubs" %% "ficus" % "1.1.1"
  case _ =>
    "net.ceedubs" %% "ficus" % "1.0.1" exclude("com.typesafe", "config")
}

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.9"
  //,"com.typesafe" % "config" % "1.2.1"
)

libraryDependencies <+= ficusDependency

buildInfoSettings

buildInfoKeys := Seq[BuildInfoKey](
  name, version, scalaVersion, sbtVersion, libraryDependencies, resolvers
)

buildInfoPackage := "io.github.morgaroth.utils.akka.core.build"

sourceGenerators in Compile <+= buildInfo

sonatypeSettings

releaseSettings

crossBuild := true

ReleaseKeys.releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies, // : ReleaseStep
  inquireVersions, // : ReleaseStep
  runClean,
  runTest, // : ReleaseStep
  setReleaseVersion, // : ReleaseStep
  publishArtifactsSigned,
  finishReleaseAtSonatype,
  commitReleaseVersion, // : ReleaseStep, performs the initial git checks
  tagRelease, // : ReleaseStep
  setNextVersion, // : ReleaseStep
  commitNextVersion, // : ReleaseStep
  pushChanges // : ReleaseStep, also checks that an upstream branch is properly configured
)

publishArtifact in Test := false

pomExtra := githubPom(name.value,"Mateusz Jaje","Morgaroth")

publishTo := publishRepoForVersion(version.value)

// Do not include log4jdbc as a dependency.
pomPostProcess := PackagingHelpers.removeTestOrSourceDependencies