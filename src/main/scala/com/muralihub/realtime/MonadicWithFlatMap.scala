package com.muralihub.realtime

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


object MonadicWithFlatMap extends App {

  import cats.Monad
  import cats.syntax.functor._ // for map
  import cats.syntax.flatMap._ // for flatMap
  import cats.Id
  import cats.data.OptionT
  import  cats.syntax._
  import cats.implicits._
  import cats._


val optionallyPreference: Option[Int] = Some(1)

def find(i: Int): Future[Option[Int]] = Future{Some(i + 1)}
  val result: Future[Option[Int]] = {
    val s: OptionT[Future, Int] = for {
   preference <- OptionT(Future.successful(optionallyPreference))
    f <- OptionT(find(preference))
    } yield f

  s.value
  }

val futureUnit = 1

  val futureString: Future[Option[String]] = Future{Option("name")}




}
