package com.muralihub.monad

import cats.syntax.either._

object TransformingEithers extends App {

  val asleft: Int = "Error".asLeft[Int].getOrElse(0)
  // here asleft returns either , we can simple do getorelse to convert to Int
  val orElse: Either[String, Int] =
    "error".asLeft[Int].orElse(2.asRight[String])
  // we still retain either

  val ensureSample: Either[String, Int] =
    -1.asRight[String].ensure("value has to be non negative")(_ > 0)
// this is similar to recover and recoverWith when using Future

  val recover: Either[String, Int] = "error".asLeft[Int].recover {
    case str: String => -1
  }

  val recoverWith: Either[String, Int] = "error".asLeft[Int].recoverWith {
    case str: String => Right(-1)
  }

  // this does left projection
  val leftMap: Either[String, Int] = "foo".asLeft[Int].leftMap(_.reverse)

  val rightMap: Either[String, Int] = 1.asRight[String].bimap(_.reverse, _ * 7)
  // res17: Either[String,Int] = Right(42)

  val leftMap = "foo".asLeft[String].bimap(_.reverse, _ * 7)
  // res18: Either[String,Int] = Left(oof)

  val either: Either[String, Int] = 123.asRight[String]

  val swapSample: Either[Int, String] = 123.asRight[String].swap

  // similarly we have toOption, toList, toTry, toValidated

  // Either can be used for fail fast error handling,  when we use sequence of computations in a flatMap if 1 fails it wont continue

  val errorhanling = for {
    a <- 1.asRight[String]
    b <- 0.asRight[String]
    c <- if (b == 0) "DIV0".asLeft[Int] else (a / b).asRight[String]

  } yield c * 100
  // res21: scala.util.Either[String,Int] = Left(DIV0)
// we could use Throwable instead of Sting for left, throwable is too broad ana we dont know what type it is
// Bester way is to use ADT

// error handling based on type

  def handleError(error: LoginError): Unit =
    error match {
      case UserNotFound(u) => println(s"User not found: $u")
      case PasswordInCorrect(u) =>
        println(s"Password incorrect: $u")
      case UnExpectedError =>
        println(s"Unexpected error")
    }

  val result1: Either[Nothing, User] = User("user", "passw0rd").asRight
  // result1: LoginResult = Right(User(dave,passw0rd))

  val result2: Either[UserNotFound, Nothing] = UserNotFound("dave").asLeft
  // result2: LoginResult = Left(UserNotFound(dave))

  val r1: Unit = result1.fold(handleError, println)
//// User(dave,passw0rd)
  val r2: Unit = result2.fold(handleError, println)
  // User not found: dave

}

sealed trait LoginError extends Product with Serializable
final case class UserNotFound(username: String) extends LoginError
final case class PasswordInCorrect(username: String) extends LoginError

case object UnExpectedError extends LoginError
case class User(userName: String, password: String) {

  type LoginResult = Either[LoginError, User]
}
