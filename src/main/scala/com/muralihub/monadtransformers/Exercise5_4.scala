package com.muralihub.monadtransformers

import cats.data.EitherT

import scala.concurrent.{Await, Future}
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global
import cats.implicits._
import cats.syntax._

object Exercise5_4 extends App {

  type Response[A] = EitherT[Future, String, A]


  def getPowerLevel(autobot: String): Response[Int] = {
    powerLevels.get(autobot) match {
      case Some(i) => EitherT.right(Future(i))
      case _ => EitherT.left(Future(s"$autobot is unreachable"))
    }
  }


  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] = for {
    a <- getPowerLevel(ally1)
    b <- getPowerLevel(ally2)

  } yield ((a + b) > 15)


  val powerLevels = Map(
    "Jazz"
      -> 6,
    "Bumblebee" -> 8,
    "Hot Rod"
      -> 10
  )


  val result = getPowerLevel("Jazz")
  println(result)

  val result2 = canSpecialMove("Jazz", "Hot Rod")
  println(result2)

  import scala.concurrent.duration._

  def tacticalReport(ally1: String, ally2: String): String = {
    val s: Future[Either[String, Boolean]] = canSpecialMove(ally1, ally2).value
    Await.result(s, 5.seconds) match {
      case Right(true) => "nice"
      case Right(false) => "error "
      case Left(s) => s"error $s"
    }

  }
}
