package com.muralihub.monad

import cats.data.{Writer, WriterT}
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import cats.Id
import cats.syntax.writer._
import com.muralihub.monad.WriterMonad.{Logged, Writer}
import cats.syntax.applicative._
import cats.instances.vector._
import scala.collection.immutable

object Exercise4_7_3 extends App {
  def slowly[A](body: => A) =
    try body
    finally Thread.sleep(100)
  def factorial(n: Int): Int = {
    val ans = slowly(if (n == 0) 1 else n * factorial(n - 1))
    println(s"fact $n $ans")
    ans
  }
  factorial(5)
//  fact 0 1
//  fact 1 1
//  fact 2 2
//  fact 3 6
//  fact 4 24
//  fact 5 120

  Await.result(
    Future.sequence(Vector(Future(factorial(3)), Future(factorial(3)))),
    5.seconds
  )

  type Writer[W, A] = WriterT[Id, W, A]
  type Logged[A] = Writer[Vector[String], A]

  def factorialWithWriter(n: Int): Logged[Int] =
    for {
      ans <- if (n == 0)
        1.pure[Logged]
      else
        slowly(factorialWithWriter(n - 1).map(_ * n))
      _ <- Vector(s"fact $n $ans").tell
    } yield ans

  val result1: (Vector[String], Int) = factorialWithWriter(3).run

  val result: immutable.Seq[(Vector[String], Int)] = Await.result(
    Future.sequence(
      Vector(
        Future(factorialWithWriter(3).run),
        Future(factorialWithWriter(3).run)
      )
    ),
    10.seconds
  )

  println(result.map { x =>
    println(s"${x._1}${x._2}")
  })

}
