package com.muralihub.monad

import cats.syntax.applicative._
// for pure
import cats.syntax.either._
import cats.MonadError
import cats.instances.either._
import cats.syntax.applicativeError._ // for raiseError etc
import cats.syntax.monadError._ // for ensure


object ErrorHandlingAndMonadError {
  type ErrorOr[A] = Either[String, A]

  val monadError = MonadError[ErrorOr, String]


  val success = 42.pure[ErrorOr]
  //Right(42)
  val failure = "Badness".raiseError[ErrorOr, Int]
  // failure: ErrorOr[Int] = Left(Badness)

  success.ensure("Number to low!")(_ > 1000)
  // res4: Either[String,Int] = Left(Number to low!)
}

trait MonadError1[F[_], E] extends Monad[F] {
  // Lift an error into the `F` context:
  def raiseError[A](e: E): F[A]
  // Handle an error, potentially recovering from it:
  def handleError[A](fa: F[A])(f: E => A): F[A]
// Test an instance of `F`,
// failing if the predicate is not satisfied:
def ensure[A](fa: F[A])(e: E)(f: A => Boolean): F[A]
}

// raiseError is pure expect it creats instance representing a Failure