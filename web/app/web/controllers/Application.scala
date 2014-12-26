package web.controllers

import akka.actor.ActorRef
import play.api.mvc._
import play.api.libs.json.JsValue
import play.api.libs.iteratee.{Iteratee, Concurrent}

object Application extends Controller {
  import play.api.Play.current
  import web.actors.Tracker._

  val globals = current.global.asInstanceOf[web.Global.type]
  lazy val tracker: ActorRef = globals.tracker
  implicit lazy val ec = globals.system.dispatcher

  def track(keyword: String) = WebSocket.using[JsValue] { _ =>
    val (out, channel) = Concurrent.broadcast[JsValue]

    val in = Iteratee.skipToEof[JsValue].map { _ =>
      tracker ! Unregister(keyword, channel)
    }

    tracker ! Register(keyword, channel)

    (in, out)
  }

}
