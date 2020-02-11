package com.muralihub

import org.scalatest.funspec.AnyFunSpec
import CatsSemiGroup._
import org.scalatest.matchers.should.Matchers._

class CatsSemiGroupTest extends AnyFunSpec {

  it("semigroup can combine various types") {

    combineInt should be(4)

    combineString should be("muraliraju")

    combineOptions should be(Option(5))

    combineMaps should be(Map(5 -> "", 1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four"))

    combineVector should be(Vector(1, 2 ,3, 4, 5))

    combineEither should be(Right(11))

    combineEitherCombination should be(Left("error"))

  }


}
