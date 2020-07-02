package com.muralihub.foldableAndTraverse

object FoldsAndFolding extends App{

  def show[A](list: List[A]): String = {
    list.foldLeft(""){(acc, item) => s"$item then  $acc"}
  }

  val result = show(List(1, 2, 3))
println(result)
  //3 then  2 then  1 then



}
