package com.muralihub.monad

import cats.syntax.either._

object TransformingEithers extends App{

  val asleft: Int = "Error".asLeft[Int].getOrElse(0)
  // here asleft returns either , we can simple do getorelse to convert to Int
  val orElse: Either[String, Int] = "error".asLeft[Int].orElse(2.asRight[String])
  // we still retain either
}
