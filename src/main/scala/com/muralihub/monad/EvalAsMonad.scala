package com.muralihub.monad

import cats.Eval

object EvalAsMonad extends App{

    // because its a monad we can do map and flatMap

  val greeting: Eval[String] = Eval
    .always{println("step1"); "Hello"}
    .map{str => println("step2"); s"$str world"}

  // if we  call greeting it wont be evaluated because of Eval.always. above function will be evaluated only when we call greeting.value
  println(greeting.value)

//  step1
//  step2
//  Hello world

  val ans = for {
  a <- Eval.now{println("Calculating A"); 40}
  b <- Eval.always{println("Calcutlationg B"); 2}
  } yield {
    println("Adding A and B")
    a + b
  }
// because we call Eval.now this part will be executed without calling ans.value
println(ans)
//  Calculating A
//    cats.Eval$$anon$4@26f67b76

  println(ans.value)
//  Calcutlationg B
//    Adding A and B
//    42
  println(ans.value)
//  Calcutlationg B
//    Adding A and B
//    42


// when we call memoize after chain of computations everything before memoize is cached
  // everything after memoize works as normal
val mem = Eval.
    always{println("mem 1") ; 10}
    .map{x =>println("mem 2") ;x + 2}
  .memoize
  .map{x => println("mem 3") ; x + 2}
  .map{x => println("mem 4");  x +  2}

  println(mem.value)
  println(mem.value)
//  mem 1
//  mem 2
//  mem 3
//  mem 4
//  16
//  mem 3
//  mem 4
//  16


 // Trampolining and Eval.defer
  // for bigint this can stack overflow
 def factorial(n: BigInt): Eval[BigInt] =
   if(n == 1) {
     Eval.now(n)
   } else {
     factorial(n - 1).map(_ * n)
   }

  // using defer

  def factorialStacksafe(n: BigInt): Eval[BigInt] =
    if(n == 1) {
      Eval.now(n)
    } else {
    Eval.defer(factorial(n - 1).map(_ * n))
    }

  // this uses heap instead of stack memory


}
