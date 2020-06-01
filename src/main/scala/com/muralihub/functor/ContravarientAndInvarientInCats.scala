package com.muralihub.functor

import cats.Contravariant
import cats.Invariant
import cats.Show
import cats.instances.string._
import cats.syntax.contravariant._
import cats.syntax.invariant._ //for imap
import cats.syntax.semigroup._ //for |+|
import cats.Monoid

object ContravarientAndInvarientInCats extends App{

val showString: Show[String] = Show[String]

val showSymbol: Show[Symbol] = Contravariant[Show].contramap(showString)((sym:Symbol) => s"${sym.name}")

  println(showSymbol.show('cats))
  //more conveniently we can use cats syntax
  showString.contramap[Symbol](_.name).show('cats)

  implicit val symbolMonoid: Monoid[Symbol] =
    Monoid[String].imap(Symbol.apply)(_.name)
  Monoid[Symbol].empty
  'a |+| 'few |+| 'words


  import cats.Functor
  import cats.instances.function._
  import cats.syntax.functor._

  val func1: Int => Double = (i: Int) => i.toDouble
  val func2: Double => Int = (d: Double) => d.toInt
// we  cant do this with standard scala

  val func3: Int => Int = func1.map(func2)
  val func33: Int => Int = func1.andThen(func2)
  val func333: Int => Int = func2.compose(func1)




// partial unification helps compilation errors if compiler is confused with 2 possible values



  import cats.instances.either._






}

//cats implementation
trait Contravariant1[F[_]] {
  def contramap[A, B](fa: F[A])(f: B => A): F[B]
}
trait Invariant1[F[_]] {
  def imap[A, B](fa: F[A])(f: A => B)(g: B => A): F[B]
}

