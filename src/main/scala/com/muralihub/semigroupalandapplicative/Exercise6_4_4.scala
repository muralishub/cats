package com.muralihub.semigroupalandapplicative

import scala.util.Try


object Exercise6_4_4 extends App {


  import cats.data.Validated

  type FormData = Map[String, String]
  type FailFast[A] = Either[List[String], A]
  type FailSlow[A] = Validated[List[String], A]

  def getValue(name: String)(data: FormData) = data.get(name).toRight(List(s"$name field not specified"))


  val getName = getValue("name") _

  println(getName(Map("name" -> "murali")))
//Right(murali)
  println(getName(Map("ne" -> "murali")))
///
  Left(List("name field not specified"))



  import cats.syntax.either._

type NumFmtExn = NumberFormatException

  def parseInt(name: String)(data: String): FailFast[Int] = Either.catchOnly[NumFmtExn](data.toInt).leftMap(_ => List(s"$name must be an integer"))

println(parseInt("age")("11"))
//Right(11)
println(parseInt("age")("1a"))
//Left(List(age must be an integer))


def nonBlank(name: String)(data: String): FailFast[String] = Right(data).ensure(List(s"$name cannot be blank"))(_.nonEmpty)

  def nonNegative(name: String)(data:Int): FailFast[Int] = Right(data).ensure(List(s"$name must be non negarive"))(_ >= 0)

  nonBlank("name")("Dade Murphy")
  // res36: FailFast[String] = Right(Dade Murphy)
  nonBlank("name")("")
  // res37: FailFast[String] = Left(List(name cannot be blank))
  nonNegative("age")(11)
  // res38: FailFast[Int] = Right(11)
  nonNegative("age")(-1)
  // res39: FailFast[Int] = Left(List(age must be non-negative))


// for sequential operation

  def readName(data: FormData): FailFast[String] = getValue("name")(data).flatMap(nonBlank("name"))

  def readAge(data: FormData): FailFast[Int] = getValue("age")(data).flatMap(nonBlank("age")).flatMap(parseInt("age")).flatMap(nonNegative("age"))

  readName(Map("name" -> "Dade Murphy"))
  // res41: FailFast[String] = Right(Dade Murphy)
  readName(Map("name" -> ""))
  // res42: FailFast[String] = Left(List(name cannot be blank))
  readName(Map())
  // res43: FailFast[String] = Left(List(name field not specified))
  readAge(Map("age" -> "11"))
  // res44: FailFast[Int] = Right(11)
  readAge(Map("age" -> "-1"))
  // res45: FailFast[Int] = Left(List(age must be non-negative))
  readAge(Map())
  // res46: FailFast[Int] = Left(List(age field not specified))


  // we can now do switching from Either to Validated to Fail Slow

  import cats.instances.list._
  import cats.syntax.apply._

  def readUser(data: FormData): FailSlow[User] = (readName(data).toValidated, readAge(data).toValidated).mapN(User.apply)

  readUser(Map("name" -> "Dave", "age" -> "37"))
  // res48: FailSlow[User] = Valid(User(Dave,37))
  readUser(Map("age" -> "-1"))
  // res49: FailSlow[User] = Invalid(List(name field not specified, age
//sm   must be non-negative))






 }





case class User(name: String, age:Int)