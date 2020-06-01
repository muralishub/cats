package com.muralihub.typeclass
import cats._
import cats.implicits._



object ShowInsteadOfPrintable extends App{

  implicit val catCanShow: Show[Cat] = Show.show[Cat](cat =>  s"${cat.name} is a ${cat.age} old ${cat.color} animal")

  println(catCanShow.show(Cat("buddy", 2, "black")))

}
