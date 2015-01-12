package web.protocols

import play.api.libs.json._
import play.api.libs.functional.syntax._ // Combinator syntax

sealed trait Error {
  def code: Int
  def message: JsValue
}

object Error {
  case class BadRequest(message: JsValue) extends Error {
    val code = 400
  }

  implicit val writes = new Writes[Error] {
    def writes(error: Error): JsValue = Json.obj(
      "code" -> error.code,
      "message" -> error.message
    )
  }
}
