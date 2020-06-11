package com.muralihub.monad

import cats.Eval

class Exercise4_6_5 extends App{
  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    as match {
      case head :: tail =>
        fn(head, foldRight(tail, acc)(fn))
      case Nil =>
        acc
    }

  def foldRightTrampolining[A, B](as: List[A], acc: Eval[B])(fn: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
      case head :: tail => Eval.defer(fn(head, foldRightTrampolining(tail, acc)(fn)))
      case Nil =>
     acc
    }
}
