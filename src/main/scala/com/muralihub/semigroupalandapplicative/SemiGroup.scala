package com.muralihub.semigroupalandapplicative

import cats.Semigroupal

import scala.collection.immutable


object SemiGroup extends App {

  import cats.syntax.either._ // for catchOnly
  def parseInt(str: String): Either[String, Int] =
    Either.catchOnly[NumberFormatException](str.toInt).
      leftMap(_ => s"Couldn't read $str")

 val result =  for {
    a <- parseInt("a")
    b <- parseInt("b")
    c <- parseInt("c")
  } yield (a + b + c)

  println(result)
//Left(Couldn't read a)


// semi Semigroupal

  //joining 2 context

  import cats.Semigroupal
  import cats.instances.option._

  val test1: Option[(Int, String)] = Semigroupal[Option].product(Some(11), Some("ss"))
  println(test1)
//Some((11,ss))
val test2: Option[(Int, String)] = Semigroupal[Option].product(Some(11), None)
println(test2)
//None

  // joining 3 or more context

  import cats.instances.option._

 val test3 =  Semigroupal.tuple3(Option(1), Option(2), Option(3))
println(test3)
//Some((1,2,3))

  val test4 =  Semigroupal.tuple3(Option(1), Option(2), Option.empty[Int])
  println(test4)
///
  None


  // we have functions to map2 to map22 to apply usersepcific functions

val test5: Option[Int] =   Semigroupal.map3(Option(1), Option(2), Option(3))(_ + _ + _)
println(test5)
//Some(6)

  val test6 = Semigroupal.map3(Option(1), Option(2), Option.empty[Int])(_ + _ + _)
println(test6)
// None


// we can user apply method for convinence

  import cats.syntax.apply._

  val test7: Option[(Int, String)] = (Option(123), Option("test")).tupled
  val test8: Option[(Int, String, Boolean)] = (Option(123), Option("test"), Option(true)).tupled

  // in addition we have mapN function that accepts an implicit functor and a function of the correct arity to combine the values
//
val test9: Option[Cat] =   (
  Option("test"),
  Option(123),
  Option("true")
  ).mapN(Cat.apply)

// internally this uses semi groupal to extract values from option and the functor to apply values to the function

  val add: (Int, Int) => Int = (a, b) => a + b

  val addResult: Option[Int] =   (Option(2), Option(3)).mapN(add)
  println(addResult)







}


case class Cat
(name: String, born: Int, color: String)
