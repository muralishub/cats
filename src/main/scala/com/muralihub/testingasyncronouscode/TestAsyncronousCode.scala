package com.muralihub.testingasyncronouscode

import scala.concurrent.Future

trait UptimeClient {

  def getUptime(hostname: String): Future[Int]
}

import cats.instances.future._
import cats.instances.list._
import cats.syntax.traverse._
import scala.concurrent.ExecutionContext.Implicits.global


class UptimeService(client: UptimeClient) {
 def getTotalUptime(hostnames: List[String]): Future[Int] = ???
 //  hostnames.traverse(client.getUptime).map(_.sum)
}



class TestUptimeClient(host: Map[String, Int]) extends UptimeClient {
  override def getUptime(hostname: String): Future[Int] = Future.successful(host.getOrElse(hostname, 0))


  def testTotalUptime() = {
    val hosts = Map("host1" -> 10, "host2" -> 6)
    val client = new TestUptimeClient(hosts)

    val service = new UptimeService(client)
    val actual: Future[Int] = service.getTotalUptime(hosts.keys.toList)
    val expected: Int = hosts.values.sum

   // assert(actual == expected)

//this wont compile

  }
}

import cats.Id

trait UptimeClientN[F[_]] {

  def getUptime(hostname: String): F[Int]
}

trait RealUptimeClientN extends UptimeClientN[Future] {
  def getUptime(hostname: String): Future[Int]
}
trait TestUptimeClientN extends UptimeClientN[Id] {
  def getUptime(hostname: String): Id[Int]

  }

class TestUptimeClientNC(hosts: Map[String, Int])
  extends UptimeClientN[Id] {
  def getUptime(hostname: String): Int =
    hosts.getOrElse(hostname, 0)
}








