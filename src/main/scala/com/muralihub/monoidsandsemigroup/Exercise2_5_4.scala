package com.muralihub.monoidsandsemigroup

import cats.implicits._

object Exercise2_5_4 {

  def add(items: List[Int]): Int = items.sum

   def add1(items: List[Int]) : Int = items.foldLeft(0)(_ |+| _)
   def add2(items: List[Int]) : Int = items.reduceLeft(_ |+| _)
   def add3(items: List[Int]) : Int = items.reduce(_ |+| _)
   def add4(items: List[Int]) : Int = items.foldLeft(cats.Monoid[Int].empty)(_ |+| _)



  def superAdd(items: List[Option[Int]]): Option[Int] = items.reduceLeft(_ |+| _)
  def superAdd2(items: List[Option[Int]]): Option[Int] = items.foldLeft(cats.Monoid[Option[Int]].empty)(_ |+| _)



def addOrders[A](list: List[Order])(implicit monoid: cats.Monoid[Order]):Order = list.foldLeft(monoid.empty)(_ |+| _)

def addAll[A](list: List[A])(implicit monoid: cats.Monoid[A]):A = list.foldRight(monoid.empty)(_ |+| _)

implicit val monoid: Monoid[Order] = new Monoid[Order] {
  override def empty: Order = Order(0, 0)

  override def combine(x: Order, y: Order): Order = Order(x.totalCost + y.totalCost, x.quality + y.quality)
}



}


case class Order(totalCost: Double, quality: Double)