package com.muralihub

import cats.Semigroup._
import cats.implicits._

object CatsSemiGroup {

// most type class in cats has their own rules
  // all type classes satisfy one rule that is associative law
  //a combine(b combine c) should be equal to (a combine b) combine c
  //as a result we can do a combine b on one thread and b combine c in other thread

val combineInt = 1 |+| 3
val combineString = "murali" |+| "raju"
val combineOptions = Option(3) |+| Option(2)
val combineMaps = Map(1 -> "one",  2 -> "two") |+| Map(3 -> "three",  4 -> "four", 5 -> "")
val combineVector = Vector(1, 2) |+| Vector(3, 4, 5)

//  val x: Either[Nothing, Int] = 5.asRight
//  val y: Either[Nothing, Int] = Right(3)

val combineEither = 5.asRight |+| 6.asRight
val combineEitherCombination: Either[String, Int] = 1.asRight[String] |+| 2.asRight |+| "error".asLeft






}
