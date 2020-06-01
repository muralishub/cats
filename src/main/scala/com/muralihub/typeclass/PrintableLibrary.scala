package com.muralihub.typeclass

object PrintableLibrary extends App{

  // use printable library to print cat
import PrintableInstances._

  val cat = Cat("buddy", 5, "black")

  val result = Printable.format(cat)

   println(result)


  import PrintableSyntax._

  cat.print
}

trait Printable[A] {
  def format(a: A): String
}

object PrintableInstances {
  implicit  val stringFormat: Printable[String] = new Printable[String] {
    override def format(a: String): String = a
  }

  implicit  val intFormat: Printable[Int] = new Printable[Int] {
    override def format(a: Int): String = a.toString
  }

  implicit val catFormat: Printable[Cat] = new Printable[Cat] {
    override def format(a: Cat): String = {
      val name = Printable.format[String](a.name)
      val age = Printable.format[Int](a.age)
      val color = Printable.format[String](a.color)

      s"$name is a $age old $color animal"
    }
  }

}

//as an Extension method
object PrintableSyntax {

  implicit class PrintableOps[A](value: A) {
    def format(implicit p: Printable[A]): String = p.format(value)
    def print(implicit p: Printable[A]): Unit = println(format(p))
  }
}

object Printable {
  def format[A](a: A)(implicit printable: Printable[A]) = printable.format(a)
  def print[A](a: A)(implicit printable: Printable[A]): Unit = println(printable.format(a))
}

final case class Cat(name: String, age: Int, color: String)

