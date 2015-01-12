package web.protocols

import play.api.libs.json._
import play.api.libs.functional.syntax._ // Combinator syntax

sealed trait Command
object Command {
  case class Track(keyword: String) extends Command
  case class Untrack(keyword: String) extends Command

  val readsTrack: Reads[Command] = (__ \ "track").read[String].map(Track)
  val readsUntrack: Reads[Command] = (__ \ "untrack").read[String].map(Untrack)
  implicit val readsCommand: Reads[Command] = readsTrack or readsUntrack
}
