package com.muralihub.semigroupalandapplicative

object MethodsOfValidated  extends App{

  import cats.data.Validated
  import cats.syntax.either._
  import cats.Semigroup
  import cats.instances.int._
  import cats.syntax.validated._

  // methods are very similar to Either


  val a: Validated[Nothing, Int] = 123.valid.map(_  *100)
  val b: Validated[String, Nothing] = "?".invalid.leftMap(_.toString)

  val bimap: Validated[String, Int] = 123.valid[String].bimap(_ + "!", _ * 2)

  val b2 = "?".invalid[Int].bimap(_ + "!", _ * 100)

  //validated is int a flatmap because it doesnt obey laws so we can use andThen instead

  32.valid.andThen{
    a => 10.valid.map{ b =>
      a + b
    }
  }

  // we can also convert back and forth between either and validated


  val e1: Either[String, Int] = 32.valid[String].toEither
  val  e2: Either[String, Int] =  "error".invalid[Int].toEither

  val e3: Validated[String, Int] = e1.toValidated

  // we can use ensure like we use for either to make sure it will fail with specific error if predicate does not hold

  val e: Validated[String, Int] = 123.valid[String].ensure("Negagive")(_ > 0)

  "fail".invalid[Int].getOrElse(0)

  "fail".invalid[Int].fold(_ + "!!!", _.toString)

}
