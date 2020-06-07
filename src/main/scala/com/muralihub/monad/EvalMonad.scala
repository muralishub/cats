package com.muralihub.monad

object EvalMonad extends App{

  // in scala standard we have eager, lazy and Memoized

  val x = {
    println("eval x")
    math.random()
  }

  //here even if we dont call x its evauated its eager, but no matter how many times its called value of x is cached
  println(x)
  println(x)
  println(x)
// so this will print the same value

  def y = {
    println("eval y")
    math.random()
  }
// this is not evaluated until its called so its lazy

  println(y)
  println(y)
  println(y)
  // this will be evaluated 3 times so will print different values each time


  lazy val z = {
    println("eval z")
    math.random()
  }
// this will be lazy so this wont be evaluated until its called but value is memoized

println(z)
println(z)
println(z)
// this will print same value


  // cats has Eval to deal with this

  import cats.Eval

  val now = Eval.now {
    println("now")
    math.random() + 1000
  }

  val later = Eval.later{
    println("later")
    math.random + 2000
  }

  val always = Eval.always{
    println("always")
    math.random + 3000}

  // this works like val
 println(now.value)
 println(now.value)
 println(now.value)

  //this works like lazy val
 println(later.value)
 println(later.value)
 println(later.value)

  // this works like def
 println(always.value)
 println(always.value)
 println(always.value)



}
