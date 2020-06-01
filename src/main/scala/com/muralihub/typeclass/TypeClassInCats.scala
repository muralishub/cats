package com.muralihub.typeclass

import cats.Show
import cats.instances.int._
import cats.instances.string._



object TypeClassInCats extends App{


  val intCanShow = Show.apply[Int]
  val stringCanShow = Show.apply[String]

  //we can use them as  print Int's and string's
val intConversionResult: String = intCanShow.show(5)
val stringConversionResult: String = stringCanShow.show("5")

// interface syntax

import cats.syntax.show._

val intSyntax: String =   5.show
val strongSyntax: String = "5".show


// if there is no implementation for a particular type in cats , we can make use of a method  that takes function as parameter
  // this is much simplier than implimenting for date by creating instance of new Show[Date]

import java.util.Date

  implicit val dataShow: Show[Date] = Show.show(date => s"${date.getTime}ms since the epoch.")






}
