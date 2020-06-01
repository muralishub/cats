package com.muralihub.monoidsandsemigroup

import cats.Monoid._
import cats.Semigroup._
import cats.implicits._
import cats.instances._

object Excercise2_3 extends App{

// how many monoids can we define for a boolean
}

trait Semigroup[A] {
  def combine(x:A, y:A): A
}

trait Monoid[A] extends Semigroup[A] {
  def empty : A
}

object Monoid {
  def apply[A](monoid: Monoid[A]): Monoid[A] = monoid

  implicit val and: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = true
    override def combine(x: Boolean, y: Boolean): Boolean = x && y
  }

  implicit val or: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = false
    override def combine(x: Boolean, y: Boolean): Boolean = x || y
  }

  implicit val either: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = false
    override def combine(x: Boolean, y: Boolean): Boolean = (x && !y) || (!x && y)
  }

  implicit val nor: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = true
    override def combine(x: Boolean, y: Boolean): Boolean = (!x || y) && (x || !y)
  }
}