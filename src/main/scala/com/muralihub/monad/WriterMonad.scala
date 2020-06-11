package com.muralihub.monad

import cats.Id
import cats.data.{Writer, WriterT}
import cats.instances.vector._
import org.scalatest.funspec.AnyFunSpec


object WriterMonad extends App{

  val w: WriterT[Id, Vector[String], Int] = Writer(Vector("message1", "message2"), 12)

  // return type is WriterT because WriterT is alias for Writer

  type Writer[W, A] = WriterT[Id, W, A]

  // we can specify just the log or the result

  import cats.syntax.applicative._

  type Logged[A] = Writer[Vector[String], A]

    val valueWriter: Logged[Int] = 12.pure[Logged]

  // If we have log and no result we can use tell

  import cats.syntax.writer._

  val logWriter: Writer[Vector[String], Unit] = Vector("log1", "log2").tell

  //If we have both value and log and to get the result we can use run

  val both: WriterT[Id, Vector[String], Int] = Writer(Vector("log1", "log2"), 12)
  val both2: Writer[Vector[String], Int] = 12.writer(Vector("log1", "log2"))


  val resultOfBoth: (Vector[String], Int) = both.run
  val resultOfBoth2: (Vector[String], Int) = both2.run

  // to get only logs or messages
  val onlyLog: Id[Vector[String]] = both.written
  val onlyVal: Id[Int] = both.value

// composing and transforming writers

  // here log will be preserved

 val writer1: WriterT[Id, Vector[String], Int] = for {
 a <- 10.pure[Logged]
 _ <- Vector("a", "b", "c").tell
 b <- 32.writer(Vector("x", "y", "z"))
  } yield (a + b)

println(writer1.run)
//(Vector(a, b, c, x, y, z),42)

//map written method
val writer2 = writer1.mapWritten(_.map(_.toUpperCase))
println(writer2.run)
//  (Vector(A, B, C, X, Y, Z),42)

// transform both at the same time
val writer3 = writer1.bimap(_.map(_.toUpperCase), _ + 1)
  println(writer3.run)
  //(Vector(A, B, C, X, Y, Z),43)

  //or
 val writer4 = writer1.mapBoth((log, res) => (log.map(_.toUpperCase), res + 1))
println(writer4.run)
  //(Vector(A, B, C, X, Y, Z),43)

  val writer5 = writer1.reset
  println(writer5.run)
//(Vector(),42)

  val writer6 = writer1.swap
  println(writer6.run)
  //(42,Vector(a, b, c, x, y, z))
  }
