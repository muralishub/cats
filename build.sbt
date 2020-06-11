name := "cats"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies +=
  "org.typelevel" %% "cats-core" % "1.0.0"

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-deprecation",
  "-feature",
  "-language:higherKinds",
  "-Ypartial-unification" // we dont need this from 2.13. this is used for . this improves constructor inference with support for partial unification
  //For example, it allows a Function1[Int, Int] with two type parameters to be passed to something expecting F[_] with only one type parameter, by using partial application. This is sort of treating Function1[Int, Int] as Function1[Int][Int] with two, individual type-parameters
)

libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0"