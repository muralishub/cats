package com.muralihub.foldableAndTraverse

import scala.concurrent.{Await, Future}


object TraverseInCats extends App{

  import cats.Traverse
  import cats.instances.future._
  import cats.instances.list._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  val hostnames = List("alpha.example.com",
    "beta.example.com",
    "gamma.demo.com"
  )

  def getUptime(hostname: String): Future[Int] =
    Future(hostname.length * 60)


  val totalUptime: Future[List[Int]] =
    Traverse[List].traverse(hostnames)(getUptime)
  Await.result(totalUptime, 1.second)
  // res1: List[Int] = List(1020, 960, 840)

  val numbers = List(Future(1), Future(2), Future(3))

  val numbers2: Future[List[Int]] = Traverse[List].sequence(numbers)

  Await.result(numbers2, 1.second)
  // res3: List[Int] = List(1, 2, 3)

  import cats.syntax.traverse._


  val tra: Future[List[Int]] = hostnames.traverse(getUptime)

  val num3: Future[List[Int]] = numbers.sequence


//  Using these meth-
//    ods we can turn an F[G[A]] into a G[F[A]] for any F with an instance of Tra-
//    verse and any G with an instance of Applicative
}
