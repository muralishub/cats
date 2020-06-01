package com.muralihub.monoidsandsemigroup

//import  cats.Monoid
import cats.implicits._


object CatsMonoids {
  val combineInt = 1 |+| 0
  val combineString = "murali" |+| ""
  val combineOptions = Option(3) |+| None
  val combineMaps = Map(1 -> "one",  2 -> "two") |+| Map.empty
  val combineVector = Vector(1, 2) |+| Vector.empty[Int]
  val combineEither = 5.asRight |+| 0.asRight
  val combineEitherCombination: Either[String, Int] = 1.asRight[String] |+| 0.asRight |+| "error".asLeft
}
