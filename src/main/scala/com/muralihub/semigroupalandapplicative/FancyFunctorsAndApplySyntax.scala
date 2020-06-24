package com.muralihub.semigroupalandapplicative

import cats.Monoid
import cats.instances.int._
import cats.instances.invariant._
import cats.instances.list._
import cats.instances.string._
import cats.syntax.apply._

import scala.collection.immutable


object FancyFunctorsAndApplySyntax extends App{

  val tupleToCat: (String, Int, List[String]) => Cat1 = Cat1.apply _

  val catToTuple: Cat1 => (String, Int, List[String]) = cat => (cat.name, cat.yearOfBirth, cat.favourateFood)

  implicit val catMonoid: Monoid[Cat1] = (
  Monoid[String],
    Monoid[Int],
  Monoid[List[String]]
  ).imapN(tupleToCat)(catToTuple)

// this will allow us to add empty and add

  import cats.syntax.semigroup._

  val f = Cat1("buddy", 2020, List("meat"))
  val g = Cat1("thanni", 2021, List("fish"))
   val r = f |+| g
println(r)
//Cat1(buddythanni,4041,List(meat, fish))


  //Future

  import cats.Semigroupal
  import cats.instances.future._
  import scala.concurrent._
  import scala.concurrent.duration._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.language.higherKinds

  val futurePair: Future[(String, Int)] = Semigroupal[Future].product(Future("Hello"), Future(1234))

  val fp = Await.result(futurePair, 2.seconds)
 println(fp)
 // (Hello,1234)


// we can also use apply syntax

  val app = (
  Future("buddy"),
  Future(2020),
  Future(List("meat"))
  ).mapN(Cat1.apply)

val a = Await.result(app, 1.second)
println(a)
//Cat1(buddy,2020,List(meat))


  // when we work on list we get cartician product
  import cats.syntax.list._


  val listPair: immutable.Seq[(Int, Int)] = Semigroupal[List].product(List(1, 2), List(3, 4))
  println(listPair)
  //List((1,3), (1,4), (2,3), (2,4))


  // Either
  // when we are dealing with Either we get fail fast instead of accumulating errors

  import cats.syntax.either._
  import cats.implicits._

  type  ErrorOr[A] = Either[Vector[String], A]

  val e: ErrorOr[(Nothing, Nothing)] = Semigroupal[ErrorOr].product(Left(Vector("one")), Left(Vector("two")))
println(e)
///  Left(Vector(one))

// we see List and Either behaving differently becuase they are monads as they work sequentially

  // when we are dealing with Future both Futures are executed because Future is executed even before calliing product
  // this is equalent to
  val aa = Future("Future 1")
  val b = Future("Future 2")
  for {
    x <- aa
    y <- b
  } yield (x, y)






}



case class Cat1(name: String, yearOfBirth: Int, favourateFood: List[String])