package com.muralihub.semigroupalandapplicative

object Exercise6_3_1 extends App {

  // product of monads

  // implement product interms of flatMap

  import cats.Monad
  import cats.syntax.semigroup._
  import cats.syntax.flatMap._
  import cats.syntax.functor._

  def product[M[_]: Monad, A, B](x: M[A], y: M[B]): M[(A, B)] =
    for {
    a <- x
    b <- y
    } yield (a, b)



}
