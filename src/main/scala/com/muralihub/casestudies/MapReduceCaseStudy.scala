package com.muralihub.casestudies

import cats.Monoid
import cats.syntax.semigroup._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}


object MapReduceCaseStudy extends App{


def foldMap[A, B: Monoid](values: Vector[A])(func: A => B): B = {
values.map(func).foldLeft(Monoid.empty[B])(_ |+| _)
}

  def foldMap2[A, B: Monoid](values: Vector[A])(func: A => B): B = {
    values.foldLeft(Monoid.empty[B])(_ |+| func(_))
  }

  def foldMap3[A, B: Monoid](values: Vector[A])(func: A => B): B = {
    values.foldLeft(Monoid.empty[B])((acc, x) =>  acc |+| func(x))
  }


  // implementing parallel foldMap
// divide data into batches
  // batch level map phase in parallel
  // batch level reduce phase in parallel, producing a local result for each batch
  //we reduce the results for each batch to a  single final result

  // there are monoid and monad implementations available from cats.instances.future

  import cats.instances.future._
  import cats.instances.int._
  import cats.{Monad, Monoid}

  Monad[Future].pure(42)
  Monoid[Future[Int]].combine(Future(1), Future(2))



def parallelFoldMap[A, B: Monoid](values: Vector[A])(func: A => B): Future[B] = {

  val numOfCores = Runtime.getRuntime.availableProcessors()
  val groupSize = (1.0 * values.size / numOfCores).ceil.toInt

  val groups: Iterator[Vector[A]] = values.grouped(groupSize)

  //create a future to foldMap each group

  val futures: Iterator[Future[B]] = groups.map { group =>
    Future {
      group.foldLeft(Monoid[B].empty)(_ |+| func(_))
    }
  }

Future.sequence(futures).map { iterable =>
    iterable.foldLeft(Monoid[B].empty)(_ |+| _)
  }

}

  def parallelFoldMap2[A, B: Monoid](values: Vector[A])(func: A => B): Future[B] = {

    val numOfCores = Runtime.getRuntime.availableProcessors()
    val groupSize = (1.0 * values.size / numOfCores).ceil.toInt

    val groups: Iterator[Vector[A]] = values.grouped(groupSize)

    //create a future to foldMap each group

    val futures: Iterator[Future[B]] = groups.map { group =>
      Future {
        foldMap(group)(func)
      }
    }

    Future.sequence(futures).map { iterable =>
      iterable.foldLeft(Monoid[B].empty)(_ |+| _)
    }

  }


  val result: Future[Int] =
    parallelFoldMap((1 to 12000).toVector)(identity)


println(Await.result(result, 1.second))

//  Reimplement parallelFoldMap using Cats’ Foldable and Traverseable
//  type classes.

  import cats.Monoid
  import cats.instances.int._
  // for Monoid
  import cats.instances.future._
  import cats.instances.vector._
  import cats.syntax.foldable._
  import cats.syntax.traverse._

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent._
  import scala.concurrent.duration._

  def parallelFoldMapWithCats[A, B: Monoid](values: Vector[A])(func: A => B): Future[B] = {
    val numOfCores = Runtime.getRuntime.availableProcessors()
    val groupSize = (1.0 * values.size / numOfCores).ceil.toInt

   values.grouped(groupSize).toVector.traverse(group => Future(group.foldMap(func))).map(_.combineAll)
  }
  val future: Future[Int] =
    parallelFoldMap((1 to 1000).toVector)(_ * 1000)
  Await.result(future, 1.second)




}
