package com.muralihub.monad

object Exercise4_10_1 {


  implicit val treeMonad = new Monad[Tree] {
    override def pure[A](a: A): Tree[A] = Leaf(a)

    override def flatMap[A, B](tree: Tree[A])(func: A => Tree[B]): Tree[B] =
      tree match {
        case Branch(l, r) => Branch(flatMap(l)(func), flatMap(r)(func))
        case Leaf(value) => func(value)
      }

     def tailRecM[A, B](a: A)(func: A => Tree[Either[A, B]]): Tree[B] =
       flatMap(func(a)) {
         case Left(value) => tailRecM(value)(func)
         case Right(value) => Leaf(value)
       }
  }
 def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
    Branch(left, right)
  def leaf[A](value: A): Tree[A] =
    Leaf(value)
}
sealed trait Tree[+A]

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]