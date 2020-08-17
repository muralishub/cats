package com.muralihub.foldableAndTraverse

import scala.collection.immutable
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Traverse extends App {

  //Traversing with Futures


  val hostnames = List("alpha.example.com",
    "beta.example.com",
    "gamma.demo.com"
  )

  def getUptime(hostname: String): Future[Int] =
    Future(hostname.length * 60)

  val allUpTimes: Future[List[Int]] = hostnames.foldLeft(Future(List.empty[Int])) {
    (acc, host) =>
      val uptime = getUptime(host)
      for {
        acc <- acc
        uptime <- uptime

      } yield acc :+ uptime
  }

  Await.result(allUpTimes, 1.second)
  // res2: List[Int] = List(1020, 960, 840)

  // we can do this using standard library which is much cleaner. Future.traverse is taking away the pain
  //We start with a list List[A] provide a function A => Future[B] and we get Future[List[B]]
  val allStandard: Future[List[Int]] = Future.traverse(hostnames)(getUptime)

  // We also have Future.sequence
  // -> start with List[Future[A]]
  // get Future[List[A]]

  // Future.sequence and Future.accumlator allow us to iterate over a seq
  // this works with any collection


  // scala traverse provides with any type of applicative


  //Traversing with applicatives

  //out applicative above is Future(List.empty[Int])

  import cats.Applicative
  import cats.instances.future._
  import cats.syntax.applicative._


  List.empty[Int].pure[Future]

  def oldCombine(
                  accum: Future[List[Int]],
                  host
                  : String
                ): Future[List[Int]] = {
    val uptime = getUptime(host)
    for {
      accum <- accum
      uptime <- uptime
    } yield accum :+ uptime
  }


  // using semigroupal combine

  import cats.syntax.apply._

  def newCombine(accum: Future[List[Int]],
                 host: String): Future[List[Int]] =

    (accum, getUptime(host)).mapN(_ :+ _)


  import scala.language.higherKinds

  def listTraverse[F[_] : Applicative, A, B]
  (list: List[A])(func: A => F[B]): F[List[B]] =
    list.foldLeft(List.empty[B].pure[F]) { (accum, item) =>
      (accum, func(item)).mapN(_ :+ _)
    }

  def listSequence[F[_] : Applicative, B]
  (list: List[F[B]]): F[List[B]] =
    listTraverse(list)(identity)

  val totalUptime = listTraverse(hostnames)(getUptime)

  import cats.instances.vector._

  val result: Vector[List[Int]] = listSequence(List(Vector(1, 2), Vector(3, 4)))

  println(result)
  val resultWith3: Vector[List[Int]] = listSequence(List(Vector(1, 2), Vector(3, 4), Vector(5, 6)))
  println(resultWith3)


  //  7.2.2.2 Exercise: Traversing with Op ons

  import cats.instances.option._

  def process(inputs: List[Int]): Option[List[Int]] =
    listTraverse(inputs)(n => if (n % 2 == 0) Some(n) else None)

  println(process(List(2, 4, 6)))
  //Some(List(2, 4, 6)
  println(process(List(1, 2, 3)))
  //None

  //7.2.2.3 Exercise: Traversing with Validated

  import cats.data.Validated
  import cats.instances.list._

  type ErrorsOr[A] = Validated[List[String], A]


  def process2(inputs: List[Int]): ErrorsOr[List[Int]] =
    listTraverse(inputs) { n =>
      if(n % 2 == 0) {
        Validated.valid(n)
      } else {
        Validated.invalid(List(s"$n is not even"))
      }
    }

 println(process2(List(2, 4, 6)))
  //Valid(List(2, 4, 6))
  println(process2(List(1, 2, 3)))
  //Invalid(List("1 is not even","3 is not even"))

}