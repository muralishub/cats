package com.muralihub

object TypeClassSample {




}



trait Combine[T] {
  def combine(a: T, B: T): T
}


object Combine {
  def apply[T](implicit  combineT: Combine[T]): Combine[T] = combineT


  implicit class CombineOps[T](x: T)(implicit combineX: Combine[T]) {
    def combine(y: T) = combineX.combine(x, y)
  }



  implicit val intCombine: Combine[Int] = new Combine[Int] {
    override def combine(a: Int, b: Int) = a + b
  }


  implicit val stringCombine: Combine[String] = new Combine[String] {
    override def combine(a: String, b: String): String = a + b
  }
}

