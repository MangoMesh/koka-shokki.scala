// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.0" // your current series x.y

ThisBuild / organization := "io.mangomesh"
ThisBuild / organizationName := "mangomesh ~ ivanmoreau"
ThisBuild / startYear := Some(2024)
ThisBuild / licenses := Seq(
  "MPL-2.0" -> url("https://www.mozilla.org/media/MPL/2.0/index.f75d2927d3c1.txt")
)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("ivanmoreau", "Iván Molina Rebolledo")
)

// publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
ThisBuild / tlSonatypeUseLegacyHost := false

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("master")

val defaultScalaVersion = "3.3.1"
ThisBuild / crossScalaVersions := Seq(defaultScalaVersion)
ThisBuild / scalaVersion := defaultScalaVersion // the default Scala

ThisBuild / tlFatalWarnings := false

ThisBuild / fork := true
ThisBuild / javaOptions ++= Seq(
  "--add-exports=java.base/jdk.internal.vm=ALL-UNNAMED"
)

lazy val root = tlCrossRootProject.aggregate(core)

lazy val core = crossProject(JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "koka-shokki",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % "2.10.0",
      "com.disneystreaming" %% "weaver-cats" % "0.8.3" % Test
    ),
    testFrameworks += new TestFramework("weaver.framework.CatsEffect")
  )

lazy val docs = project.in(file("site")).enablePlugins(TypelevelSitePlugin)
