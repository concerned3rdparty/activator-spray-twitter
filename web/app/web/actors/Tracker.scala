package web.actors

import akka.actor._
import play.api.libs.json.JsValue
import play.api.libs.iteratee.Concurrent.Channel

class Tracker extends Actor {
  import Tracker._

  def receive = {
    case cmd: TrackCommand =>
      getWorker(cmd.keyword) forward cmd
  }

  private def getWorker(key: String): ActorRef =
    context.child(key) getOrElse context.actorOf(TrackWorker.props(key), key)

}

object Tracker {
  trait TrackCommand {
    def keyword: String
  }
  case class Register(keyword: String, channel: Channel[JsValue]) extends TrackCommand
  case class Unregister(keyword: String, channel: Channel[JsValue]) extends TrackCommand

  def props = Props[Tracker]
}
