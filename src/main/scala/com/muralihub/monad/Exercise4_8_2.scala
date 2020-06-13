package com.muralihub.monad

import cats.data.Reader

object Exercise4_8_2 extends App {
///// this is usefull when passing configuraion as parameter
//  Our configuration will consist of two databases: a list of valid users
//  and a list of their passwords

  case class DB(usersNames: Map[Int, String], passwords: Map[String, String])

//  Start by crea ng a type alias DbReader for a Reader that consumes a Db as
//  input

  type DbReader[A] = Reader[DB, A]

  def findUsername(userId: Int): DbReader[Option[String]] =
    Reader(db => db.usersNames.get(userId))

  def checkPassword(username: String, password: String): DbReader[Boolean] =
    Reader(db => db.passwords.get(username).contains(password))

  def checkLogin(userId: Int, password: String): DbReader[Boolean] =
    for {
      user <- findUsername(userId)
      status <- checkPassword(user.getOrElse(""), password)
    } yield status


  val users = Map(
    1 -> "dade",
    2 -> "kate",
    3 -> "margo"
  )
  val passwords = Map(
    "dade" -> "zerocool",
    "kate" -> "acidburn",
    "margo" -> "secret")


  val db = DB(users, passwords)

  println(checkLogin(1, "zerocool").run(db))
  println(checkLogin(4, "davinci").run(db))
//  true
//  false
}
