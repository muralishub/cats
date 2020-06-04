package com.muralihub.monad

import scala.util.Try

object CatsEither extends App{

  val either1: Right[Nothing, Int] = Right(2)
  val either2: Right[Nothing, Int] = Right(1)

  val result: Either[Nothing, Int] = for {
  e1 <- either1
  e2 <- either2
  } yield (e1 + e2)

  // we can make this type explicitly to be Either[String, Int]

  import cats.syntax.either._

  val a: Either[String, Int] = 3.asRight[String]
  val b: Either[String, Int] = 4.asRight[String]

  val result2: Either[String, Int] = for {
  x <- a
  y <- b
  } yield x * x + y * y

  // this version handled type checking better because cats version returns Either instead of Left and Right's
  // standard one things left is nothing

  val catchOnly: Either[NumberFormatException, Int] = Either.catchOnly[NumberFormatException]("foo".toInt)
 //Left(java.lang.NumberFormatException: For input string: "foo")

 val nonFatel: Either[Throwable, Nothing] =  Either.catchNonFatal(sys.error("badness"))
//Left(java.lang.RuntimeException: badness)

  // creating Either from other types
 val fromTry: Either[Throwable, Int] =  Either.fromTry(Try("foo".toInt))
  //Left(java.lang.NumberFormatException: For input string: "foo")
 val fromOption = Either.fromOption[String, Int](None, "Badness")


}
