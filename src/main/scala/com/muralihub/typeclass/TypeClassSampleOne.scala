package com.muralihub.typeclass

object   TypeClassSampleOne {
//calling type class by Interface object
 import JsonWriterInstances.stringWriter
  Json.toJson("test")
  //calling type class by Interface syntax
  import JsonSyntax._
  "test".toJson
}
final case class Person(name: String, email: String)

//This is our type class
trait JsonWriter[A] {
  def write(value: A): Json
}

object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] = new JsonWriter[String] {
    override def write(value: String): Json = JsString(value)
  }

  implicit val personWriter: JsonWriter[Person] = new JsonWriter[Person] {
    override def write(value: Person): Json = JsObject(Map("name" -> JsString(value.name), "email" -> JsString(value.email)))
  }

    //what if we want to handle JsonWriter[Option[String]] we need to duplicate instances to handle this with option
   //we can also create a definition like this

  implicit def optionWriter[A](implicit writer: JsonWriter[A]): JsonWriter[Option[A]] = new JsonWriter[Option[A]] {
    override def write(value: Option[A]): Json = value match {
      case Some(x) => writer.write(x)
      case _ => JsNull
    }
  }







}


//Exposing type class interface by Interface object

object Json{
  def toJson[A](value: A)(implicit writer: JsonWriter[A]): Json = writer.write(value)
}

//Exposing type class interface by using Interface Syntax
// We create extension method  and cats uses this technique

object JsonSyntax {
  implicit class JsonWriterOps[A](value: A){
    def toJson(implicit writer: JsonWriter[A]): Json = writer.write(value)
  }
}





sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
case object JsNull extends Json