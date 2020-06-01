package com.muralihub.functor

import cats.instances.function._
import cats.syntax.functor._

import scala.concurrent.{ExecutionContext, Future}


object CatsFunctor extends App{

  val func1: Int => Double = (i:Int) => i.toDouble
  val func2: Double => Double = (d: Double) => d * 2

  val one = func1.map(func2)(2)

  val two = func1.andThen(func2)(2)

  val three = func2(func1(2))

  val four = func2.compose(func1)(2)

 //Here we are lazily queing up operations similar to Future until we pass a value its not executed
  val function1 = ((i: Int) => i.toDouble).map(x => x + 1).map(y => y *2).map(z => z + "!")
  val result = function1(2)


  //Functors in cats

  import cats.Functor
  import cats.instances.list._
  import cats.instances.option._

  val list1  = List(1, 2, 3)

  val list2  = Functor[List].map(list1)(_ * 2)

  val option = Option(2)
  val option2 =  Functor[Option].map(option)(_ * 2)

  // lift in Functor

  val func: Int => String = (x: Int) => x.toString

  val liftedFunc: Option[Int] => Option[String] = Functor[Option].lift(func)

  val liftedList: List[Int] => List[String] = Functor[List].lift(func)


  val func11 = (a: Int) => a + 1
  val func21 = (a: Int) => a * 2
  val func31 = (a: Int) => a + "!"
  val func4: Int => String = func11.map(func21).map(func31)


  def doMath[F[_]](start: F[Int])(implicit functor: Functor[F]): F[Int] = start.map(n => n + 1 *2)


 val result1 =  doMath(Option(2))
 val result2 =  doMath(List(2, 3))


  // We can define a functor by simply defining a map function

  implicit val opitonFunctor: Functor[Option] = new Functor[Option] {
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }

  // what if we have to pass external dependency for like ExecutionContent for Future

  implicit def futureFunctor(implicit ec: ExecutionContext): Functor[Future] = new Functor[Future] {
    override def map[A, B](fa: Future[A])(f: A => B): Future[B] = fa.map(f)
  }





}
