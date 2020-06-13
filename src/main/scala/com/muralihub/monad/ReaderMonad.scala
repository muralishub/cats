package com.muralihub.monad

import cats.Id
import cats.data.{Kleisli, Reader}

object ReaderMonad extends App {

  // We can convert function A => B to [A, B]

  val cat = Cat("buddy", "fish")

  val catName: Reader[Cat, String] = Reader(cat => cat.name)
  val catFood: Reader[Cat, String] = Reader(cat => cat.favoriteFood)

  println(catName.run(cat))
  //buddy
  println(catFood.run(cat))
  //fish

  //composing readers

  val greetKitty = catName.map(x => s"Hello $x")
  println(greetKitty.run(Cat("buddy", "fish")))
// Hello buddy

  val feedKitty: Reader[Cat, String] = Reader(
    cat => s"Have a nice bowl of ${cat.favoriteFood}"
  )

  val result: Id[String] = feedKitty(Cat("buddy", "fish"))
  println(result)
// Have a nice bowl of fish

  val usingFlatMap =
    for {
      greet <- greetKitty
      feed <- feedKitty
    } yield s"$greet $feed"

  val result2: Id[String] = usingFlatMap(Cat("buddy", "fish"))
  println(result2)

}

case class Cat(name: String, favoriteFood: String)
