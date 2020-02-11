package com.muralihub

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers._

class TypeClassSampleTest extends AnyFunSpec {

  it("type class can combine Ints and strings") {

   Combine.apply[Int](Combine[Int]).combine(1, 2) should be(3)
   Combine[Int].combine(1, 2) should be(3)
   Combine[String].combine("murali", "raju") should be("muraliraju")

    import Combine.CombineOps
    5.combine(6) should be(11)
    "murali".combine("raju") should be("muraliraju")
  }
}
