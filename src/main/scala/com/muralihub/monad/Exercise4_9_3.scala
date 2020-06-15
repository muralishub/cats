package com.muralihub.monad

import cats.data.State

import scala.collection.immutable

object Exercise4_9_3 extends App {

  //post order calculator
  type CalcState[A] = State[List[Int], A]

  def evalOne(sys: String): CalcState[Int] =

    sys match {
      case "+" => operator(_ + _)
      case "_" => operator(_ - _)
      case "*" => operator(_ * _)
      case "/" => operator(_ / _)
      case a => operand(a.toInt)
    }

  def operand(num: Int): CalcState[Int] =
    State[List[Int], Int] { stack =>
      (num :: stack, num)
    }

  def operator(func: (Int, Int) => Int): CalcState[Int] = State[List[Int], Int] {
    case b :: a :: tail =>
      val ans = func(a, b)
      (ans :: tail, ans)
    case _ => sys.error("fail")
  }


  println(evalOne("42").runA(Nil).value)
  //42


  val program = for {
    _ <- evalOne("1")
    _ <- evalOne("2")
    ans <- evalOne("+")
  } yield ans

  println(program.run(Nil).value)
  //(List(3),3)

  import cats.syntax.applicative._

  def evalAll(input: List[String]): CalcState[Int] =
    input.foldLeft(0.pure[CalcState]) { (a, b) =>
      a.flatMap(_ => evalOne(b))
    }

  //splits strings to symbols
def evalInput(inphut: String) =
  evalAll(inphut.split(" ").toList).runA(Nil).value

  evalInput("1 2 + 3 4 + *")
  //21


}
