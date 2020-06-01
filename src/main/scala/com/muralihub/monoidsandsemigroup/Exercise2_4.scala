package com.muralihub.monoidsandsemigroup

class Exercise2_4 {

}
object MonoidForSets {
  def apply[A](monoid: Monoid[A]): Monoid[A] = monoid


  def addSets[A](x: Set[A], y:Set[A]): Monoid[Set[A]] = new Monoid[Set[A]] {
    override def empty: Set[A] = Set.empty[A]

    override def combine(x: Set[A], y: Set[A]): Set[A] = (x ++  y)
  }
}