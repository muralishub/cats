package com.muralihub.monadtransformers

import cats.data.Writer
import  cats.syntax._
import cats.implicits._

import scala.util.Try

object UsagePatterns extends App{

  type Logged[A] = Writer[List[String], A]

  def parseInt(s: String): Logged[Option[Int]] = {
    Try(s.toInt).toOption match {
      case Some(i) => Writer(List(s"parsed $i"), Some(i))
      case _ => Writer(List(s"cannot parse $s"), None)
    }
  }


  def add(a: String, b: String, c: String): Logged[Option[Int]] = {
    import cats.data.OptionT

    val result = for {
    x <- OptionT(parseInt(a))
    y <- OptionT(parseInt(b))
    z <- OptionT(parseInt(c))
    } yield x + y + z
     result.value
  }


  val result: Logged[Option[Int]] = add("1", "2", "3")
  println(result)
///  WriterT((List(parsed 1, parsed 2, parsed 3),Some(6)))




}
