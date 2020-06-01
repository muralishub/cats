package com.muralihub.typeclass


import cats.implicits._


object Eq_typesafetycomparision extends App{

  //interface syntax for eq is === for equality and =!= for inequality provided there is an instance of Eq[A] in scope

  import cats.Eq
  import cats.instances.int._

 val eqInt = Eq[Int]

 val returnsTrue =  eqInt.eqv(123, 123)

  val returnsFalse = eqInt.eqv(22, 33)

  // if we try to compare 2 different types we get compilation error

  import cats.instances.int._

  import cats.instances.option._


  (Some(1) : Option[Int]) === (None : Option[Int])

  Option(1) === Option.empty[Int]

  Option(5) === Option(4)

  //compiler will complain
 // Option(6) === Option("")


 // val s = 2 == Some(2)


  import cats.syntax.option._

  1.some === none[Int]

  1.some =!= none[Int]

  // we can define our own instances of Eq for our own types


  import java.util.Date
  import cats.instances.long._


  implicit val dateEq: Eq[Date] = Eq.instance[Date]{(date1, date2) => date1.getTime === date2.getTime}

  val a = new Date()
  val b = new Date()

  //we can do
  a === b






}
