package com.muralihub.functor



object ContravariantAndInvarientFunctor {

  implicit def boxPrintable[A](implicit printable: Printable[A]): Printable[Box[A]] = new Printable[Box[A]] {
    override def format(box: Box[A]): String = {
     printable.format(box.value)
    }
  }
}

trait Printable[A] {
  self =>
  def format(value: A): String

  def contramap[B](func: B => A): Printable[B] = new Printable[B] {
    override def format(value: B): String = self.format(func(value))
  }

  implicit val stringPrintable: Printable[String] =
    new Printable[String] {
      def format(value: String): String =
        "\"" + value + "\""
    }

  implicit val booleanPrintable: Printable[Boolean] =
    new Printable[Boolean] {

      def format(value: Boolean): String =
        if(value) "yes" else "no"
    }
}

final case class Box[A](value: A)
