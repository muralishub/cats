package com.muralihub.foldableAndTraverse


import cats.Foldable
import cats.instances.list._


object FoldableInCats extends App{

  val list = List(1, 2, 3)

  val result1 = Foldable[List].foldLeft(list, 0)(_ + _)
println(result1)
//6

  // Vectors and Stream work the similar way and so as Option

  import cats.instances.option._

  val mayBeInt = Option(2)

  val result2 = Foldable[Option].foldLeft(mayBeInt, 2)(_ * _)
  println(result2)
//4


  //foldRight implementation is different it uses Eval for stack safety
  import cats.Eval
  import cats.Foldable
//  def foldRight[A, B](fa: F[A], lb: Eval[B])
//                     (f: (A, Eval[B]) => Eval[B]): Eval[B]

  val bigData = (1 to 100000).toStream
//
//  bigData.foldRight(0L)(_ + _)
    // this can cause stack overflow

  // foldRight forces us to use Eval
  import cats.instances.stream._
  Foldable[Stream].foldRight(bigData, Eval.now(0L)){(num, eval) => eval.map(_ + num)}


  //Folding with Monoids

  //cats provides various familar method to use with Foldable

  //find, nonEmpty, forAll, toList, isEmpty etc

  val e = Foldable[Option].nonEmpty(Some(2))
println(e)
//true

  val f = Foldable[List].find(List(1, 2, 3))(_ % 2 == 0)
println(f)
//Some(2)

  // We also have combineAll and foldMap

  import cats.instances.int._

  val c = Foldable[List].combineAll(List(1, 2, 3))
println(c)
  //6

  import cats.instances.string._

  val fm = Foldable[List].foldMap(List(1, 2, 3))(_.toString)
println(fm)
//123

  // We can also compose Foldables to support deep traversal of nested sequences

  import cats.instances.vector._

  val ints = List(Vector(1, 2, 3), Vector(4, 5, 6))
  val ca = (Foldable[List] compose Foldable[Vector]).combineAll(ints)
println(ca)
    //21


  // Syntax for foldable

  // All methods has syntax in cats

  import cats.syntax.foldable._
  val sca = List(1, 2, 3).combineAll
  println(sca)
  //6
  val sfm = List(1, 2, 3).foldMap(_.toString)
 println(sfm)
  //123









}
