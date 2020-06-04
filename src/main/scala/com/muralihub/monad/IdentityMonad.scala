package com.muralihub.monad



object IdentityMonad extends App{
  import cats.Monad
  import cats.syntax.functor._ // for map
  import cats.syntax.flatMap._ // for flatMap
  import cats.Id // we can pass non monads to function below

def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
    for {
      x <- a
      y <- b
    } yield x*x + y*y

// we can pass non monads to above function by using this Id typed parameters
  val result: Id[Int] = sumSquare(2: Id[Int], 3: Id[Int])
  println(result) // this will print 13

  val a: Id[Int] = Monad[Id].pure(2)
  val b: Id[Int] = Monad[Id].flatMap(a)(_ + 1)

  import cats.syntax.functor._
  import cats.syntax.flatMap._

val r: Id[Int] =  a.flatMap(x => b.map(y => (x + y)))

val r2:Id[Int] = for {
x <- a
y <- b
} yield (x + y)

  println(r)
  println(r2)



}
