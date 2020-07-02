package com.muralihub.foldableAndTraverse

import scala.collection.GenTraversableOnce

object Exercise7_2_2 extends App{
  // implement map, flatMap, filter, and sum using foldRight



  def customMap[A, B](list: List[A])(f: A => B): List[B] = list.foldRight(List.empty[B])((i, acc) => f(i) :: acc)

  val result = customMap(List(1, 2, 3))(_ + 1)
//List(2, 3, 4)

  def customFlatMap[A, B](list: List[A])(f: A => List[B]): List[B] = list.foldRight(List.empty[B])((i, acc) => f(i) :::  acc)
  val addWithWrapper = (x: Int) => List(x + 1)
  val result2 = customFlatMap(List(1, 2, 3))(a => List(a + 1))
//List(2, 3, 4)

  def customFilter[A](list: List[A])(f: A => Boolean): List[A] = list.foldRight(List.empty[A])((i, acc) => if(f(i)) i :: acc else acc )

  println(customFilter(List(1, 2, -1))(_ > 0))
 // List(1, 2)

def customSum(list: List[Int]) = list.foldRight(0)(_ + _)

}
