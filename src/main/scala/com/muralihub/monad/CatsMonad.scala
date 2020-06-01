package com.muralihub.monad

import cats.Monad

import scala.util.Try
import cats.Monad._
import cats.instances._


object CatsMonad extends App {

  def parseInt(string: String): Option[Int] = Try{string.toInt}.toOption

  def divide(a: Int, b: Int): Option[Int] = if(b == 0) None else Some(a/b)

  // this has fail fast behavior,if any step in process if its a None result will be None
  def stringDivideby(aStr: String, bStr: String): Option[Int] =
    parseInt(aStr).flatMap{ aNum =>
      parseInt(bStr).flatMap { bNum =>
        divide(aNum, bNum)
      }
    }

  def stringDivideby2(aStr: String, bStr: String): Option[Int] =
    for {
    aNum <- parseInt(aStr)
    bNum <- parseInt(bStr)
    result <- divide(aNum, bNum)
    } yield result


  // This is the same case for List and Future they are no different . if we use Future if first one fails its a Future.Failure it wont go to second comutation
  //for comprehension or flatMap its all about sequencing so Future is executed in sequence if we have Future instead of Opiton


def monadLaws[F](implicit monad: Monad[Option[Int]]) = {

  val f: Int => Option[Int] = (x:Int) => Option(x)
  val g = (x: String) => Option(x)

 //left identity
 monad.pure(2).flatMap(f) == f(2)
 //right identity
  monad.flatMap(monad.pure(2)) == monad
  // associativity flat mapping over 2 functions and g is same as flat mapping f and then flatmapping g


 // m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))



}





}

trait MonadLocal[F[_]] {
  def pure[A](value: A): F[A]
  def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]
}
