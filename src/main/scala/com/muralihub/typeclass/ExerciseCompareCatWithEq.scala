package com.muralihub.typeclass

import cats.Eq
import cats.implicits._


object ExerciseCompareCatWithEq extends App{

  implicit val catEquality: Eq[Cat] = Eq.instance[Cat]((c1, c2) => (c1.name === c2.name) && (c1.age === c2.age) && (c1.color === c2.color))
  val cat1 = Cat("Garfield",  38, "orange and black")
  val cat2 = Cat("Heathcliff", 33, "orange and black")
  val result = cat1 === cat2
  val result2 = cat1 =!= cat2

println(result)
}
