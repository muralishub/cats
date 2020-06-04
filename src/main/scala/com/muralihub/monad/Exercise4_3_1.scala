package com.muralihub.monad

object Exercise4_3_1 extends App{

  import cats.Id

  def pure[A](a: A): Id[A] = a
  def map[A, B](fa: Id[A])(f: A => B): Id[B] = f(fa)
  def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B] = f(fa)


  println(pure(123))
  println(map(123)(x => x  + 1))
  println(flatMap(123)(x => x  + 1))

//  implementation of map and flatMap is same thing
  // its magic its like Option(3) is treated the same as 3


}




