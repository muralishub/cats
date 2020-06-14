package com.muralihub.monad

import cats.Eval
import cats.data.{IndexedStateT, State}


object StateMonad extends App{


  val a = State[Int, String]{value =>
    (value, s"value is $value")
  }

  val both = a.run(2).value
  println(both)
  //(2,value is 2)
  val onlyVal = a.runA(2).value
  println(onlyVal)
  //value is 2
  val onlyState = a.runS(2).value
  println(onlyState)
  //2

  // composing and transforming state

  val step1 = State[Int, String]{s =>
    val ans = s + 1
    (ans, s"result of step1: $ans")
  }

  val step2 = State[Int, String] { s =>
    val ans = s + 2
    (ans, s"value of step2:  $ans")
  }

  val result: IndexedStateT[Eval, Int, Int, (String, String)] = for {
  a <- step1
  b <- step2
  } yield (a , b)

val (state, res) = result.run(3).value
println(state, res)
 // (6,(result of step1: 4,value of step2:  6))
// state is threaded form state to state in a for comprehension even if we dont interact with it

//state monad represents each step as a computation as a instance and compose the steps using the standard monad operations


  // some primitive steps

  val getDemo: State[Int, Int] = State.get[Int]

println(getDemo.run(10).value)
  //(10,10)

  val setDemo: State[Int, Unit] = State.set[Int](10)
println(setDemo.run(10).value)
  //(10,())

  val pureDemo: State[Int, String] = State.pure[Int, String]("resullt")
println(pureDemo.run(3).value)
//(3,resullt)

  val inspectDemo: State[Int, String] = State.inspect[Int, String](_ + "!")
println(inspectDemo.run(2).value)
  //(2,2!)

  val modifyDemo: State[Int, Unit] = State.modify[Int](_ + 1)
println(modifyDemo.run(10).value)
  //(11,())

  import State._

  val program: IndexedStateT[Eval, Int, Int, (Int, Int, Int)] = for {
  a <- get[Int]
  _ <- set[Int](a + 1)
  b <- get[Int]
    _ <- modify[Int](_ + 1)
  c <- inspect[Int, Int](_  + 1000)
  }yield (a, b, c)


  val (state1, result1) = program.run(1).value

}
