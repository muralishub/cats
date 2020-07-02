package com.muralihub.foldableAndTraverse

object Exercise7_1_2 extends App {


  val foldLeftResult = List(1, 2, 3).foldLeft(List.empty[Int])((a, i) => i :: a)
  println(foldLeftResult)
  //  List(3, 2, 1)

  val foldRightResult = List(1, 2, 3).foldRight(List.empty[Int])((i, a) => i :: a)
  println(foldRightResult)
  //List(1, 2, 3)

}
