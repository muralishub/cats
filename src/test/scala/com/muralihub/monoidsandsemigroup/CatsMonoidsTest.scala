package com.muralihub.monoidsandsemigroup

import com.muralihub.monoidsandsemigroup.CatsMonoids.{combineEither, combineEitherCombination, combineInt, combineMaps, combineOptions, combineString, combineVector}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers._

class CatsMonoidsTest extends AnyFunSpec {
  it("monoids combine with empty") {

    combineInt should be(1)

    combineString should be("murali")

    combineOptions should be(Option(3))

    combineMaps should be(Map(1 -> "one", 2 -> "two"))

    combineVector should be(Vector(1, 2))

    combineEither should be(Right(5))

    combineEitherCombination should be(Left("error"))

  }
}
