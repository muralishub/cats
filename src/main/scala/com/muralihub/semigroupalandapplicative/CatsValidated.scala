package com.muralihub.semigroupalandapplicative

import cats.data.NonEmptyVector
import cats.syntax.validated

import scala.util.Try


object CatsValidated extends App{

  import cats.Semigroupal
  import cats.data.Validated
  import cats.instances.list._ // for Monoid


  type AllErrorsOr[A] = Validated[List[String], A]
 val result =  Semigroupal[AllErrorsOr].product(
    Validated.invalid(List("Error 1")),
    Validated.invalid(List("Error 2"))
  )
  println(result)
  //Invalid(List(Error 1, Error 2))

    // we can widen the return type to return Validated using smart constructors


  val v: Validated[List[String], Int] = Validated.valid[List[String], Int](123)
  val e: Validated[List[String], Int] =  Validated.invalid[List[String], Int](List("error1"))


  // we can also use extension methods same like Either

  import cats.syntax.validated._

  val v1: Validated[List[String], Int] = 123.valid[List[String]]
  val e1: Validated[List[String], Int] = List("error").invalid[Int]


  import cats.syntax.applicative._
  import cats.syntax.applicativeError._


  val v2: AllErrorsOr[Int] = 123.pure[AllErrorsOr]
  val e2: AllErrorsOr[Int] = List("erorr1").raiseError[AllErrorsOr, Int]



  // Some more helper functions

  val v3: Validated[NumberFormatException, Int] = Validated.catchOnly[NumberFormatException]("e".toInt)
  val v4: Validated[Throwable, Nothing] = Validated.catchNonFatal(sys.error("error"))
  val v5: Validated[String, Int] = Validated.fromEither(Left("eroro"))
  val v6 = Validated.fromOption[String, Int](None ,"badness")
  val v7 =  Validated.fromTry(Try("s".toInt))



  // combining instanses

  import cats.instances.string._

  Semigroupal[AllErrorsOr]

  import cats.syntax.apply._

  (
  "error".invalid[Int],
  "error2".invalid[Int]
  ).tupled


  import cats.instances.vector._

  (
  Vector(404).invalid[Int],
  Vector(500).invalid[Int]
  )

  // we can use nonempty vector or list from cats to avoid throwing if vector list is empty

  import cats.data.NonEmptyVector._

  (
  NonEmptyVector.of("Error a").invalid[Int],
  NonEmptyVector.of("Error b").invalid[Int]
  ).tupled



}
