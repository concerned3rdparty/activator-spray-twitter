package web.actors

import akka.actor._
import play.api.libs.json.JsValue
import play.api.libs.iteratee.Concurrent.Channel

class Tracker extends Actor {
  import Tracker._

  def receive = {
    case Register(keyword, channel) =>
      getWorker(keyword) forward TrackWorker.Register(channel)
    case Unregister(keyword, channel) =>
      getWorker(keyword) forward TrackWorker.Unregister(channel)
    case UnregisterFromAll(channel) =>
      context.children.foreach(_ forward TrackWorker.Unregister(channel))
  }

  private def getWorker(key: String): ActorRef =
    context.child(key) getOrElse context.actorOf(TrackWorker.props(key), key)
}

object Tracker {
  case class Register(keyword: String, channel: Channel[JsValue])
  case class Unregister(keyword: String, channel: Channel[JsValue])
  case class UnregisterFromAll(channel: Channel[JsValue])

  def props = Props[Tracker]
}
