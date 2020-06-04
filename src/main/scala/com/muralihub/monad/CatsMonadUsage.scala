package com.muralihub.monad


import cats.syntax.MonadSyntax
import cats.instances.list._
import cats.instances.option._
import cats.instances.future._
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global


object CatsMonadUsage {
  import cats.Monad
val opt1 = Monad[Option].pure(2)
  //Some(2)
  val opt2 = Monad[Option].flatMap(opt1)(x => Some(x + 1))
  //Some(3)

  val opt3 = Monad[Option].map(opt1)(x => x + 1)
  // Some(3)

  val list1 = Monad[List].pure(2)

val list2 = Monad[List].flatMap(list1)(x => List(x, x+ 1))
  // List(2, 3)

  val list3 = Monad[List].map(list1)(x => x + 1)


  val f = Monad[Future]

  val future = Monad[Future].flatMap(f.pure(2))(x => f.pure(x + 3))
  Await.result(future, 2.seconds)

  import cats.instances.option._ // for Monad
  import cats.instances.list._ // for Monad
  import cats.syntax.applicative._ // for pure

  1.pure[Option]
  5.pure[List]

  import cats.Monad
  import cats.syntax.functor._ //for map
  import cats.syntax.flatMap._ //for flatMap

  def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
    a.flatMap(x => b.map(y => x*x + y*y))


  import cats.instances.option._
  import cats.instances.list._


  sumSquare(Option(1), Option(2))
  sumSquare(List(1), List(2, 3))

  // we can do the same thing with for comprehension compiler will do ther right thing in terms flatmap or map

  def sumSquare2[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] =
    for {
      x <- a
      y <- b
    } yield (x * x + y * y)

  sumSquare2(Option(1), Option(2))
  sumSquare2(List(1), List(2, 3))




}
