package com.muralihub.monadtransformers
import cats.Id
import cats.data.{ReaderT, StateT, WriterT}

import scala.collection.immutable

object MonadTransformers  extends App{

  //cats had monad transformers where monad is suffixed with T, like OptionT EitherT etc


  // example for OptionT to compose List and Option

    // we can use OptionT[List, A], aliased to ListOption[A] for convinence to transforom List[Option[A]] into a single monad

  //we dont need to import cats.syntax.functor or this cats.syntax.flatMap we get it from OptionT

  import cats.data.OptionT
  import cats.Monad
  import cats.instances.list._
  import cats.syntax.applicative._

  type ListOption[A] = OptionT[List, A]

  val result1: ListOption[Int] = OptionT(List(Option(2)))
  val result2: ListOption[Int] = 32.pure[ListOption]

  // we use value to unpack it
  val s: List[Option[Int]] = result1.value

  val result3: ListOption[Int] = result1.flatMap((x: Int) => result2.map((y: Int) => (x +  y)))

println(result3)
  //OptionT(List(Some(34)))



  // for either we deal with 2 types so we can use type alias

  import cats.instances.either._

  type ErrorOr[A] = Either[String, A]

  type ErrorOrOption[A] = OptionT[ErrorOr, A]

  // we can use map flatmap and pure as normal monads

  val e1: ErrorOrOption[Int] = 10.pure[ErrorOrOption]
  val e2 = 20.pure[ErrorOrOption]

  val eResult: OptionT[ErrorOr, Int] = e1.flatMap(x => e2.map(y => x + y))
 println(eResult)
//OptionT(Right(Some(30)))


  // how to deal with 3 monads

  import cats.data.{OptionT, EitherT}
  import scala.concurrent.Future


  type FutureEither[A] = EitherT[Future, String, A]
  type FutureEitherOption[A] = OptionT[FutureEither, A]

  import cats.instances.future._ // for monad
 import scala.concurrent.Await
 import scala.concurrent.ExecutionContext.Implicits.global  // with out this it will complain not enough arguments

  import scala.concurrent.duration._

  val futureEitherOr: OptionT[FutureEither, Int] = for {
  a <- 10.pure[FutureEitherOption]
  b <- 20.pure[FutureEitherOption]
  } yield (a + b)

println(futureEitherOr)


// constructing and unpacking instances

  // we can use apply or pureit

  // both are same types
  val errorStack1: OptionT[ErrorOr, Int] = OptionT[ErrorOr, Int](Right(Some(2)))

  val errorStack2: ErrorOrOption[Int] = 2.pure[ErrorOrOption]


  val r1: ErrorOr[Option[Int]] = errorStack1.value
  println(r1)
//Right(Some(2))

  val r2: ErrorOr[Option[Int]] = errorStack2.value

  println(r2)
  //Right(Some(2))

  val r3: Either[String, Int] = errorStack2.value.map(_.getOrElse(0))


  // we have to use value twice to unpack 2 monadT's

  val intermediate: FutureEither[Option[Int]] = futureEitherOr.value


val r4: Future[Either[String, Option[Int]]] = intermediate.value

  val r5: Either[String, Option[Int]] = Await.result(r4, 5.seconds)

import cats.Id
  //Default instances
type Reader[E, A] = ReaderT[Id, E, A]

  type Writer[W, A] = WriterT[Id, W, A]

type State[S, A] = StateT[Id, S, A]


}
