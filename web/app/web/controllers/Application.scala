package web.controllers

import akka.actor.ActorRef
import play.api.mvc._
import play.api.libs.json.{Json, JsValue, JsSuccess, JsError}
import play.api.libs.iteratee.{Iteratee, Concurrent}
import web.protocols.{Command, Error}

object Application extends Controller {
  import play.api.Play.current
  import web.actors.Tracker._

  val globals = current.global.asInstanceOf[web.Global.type]
  lazy val tracker: ActorRef = globals.tracker
  implicit lazy val ec = globals.system.dispatcher

  def index = Action {
    Ok(web.views.html.Index())
  }

  def track = WebSocket.using[JsValue] { _ =>
    val (out, channel) = Concurrent.broadcast[JsValue]

    val in = Iteratee.foreach[JsValue] { msg =>
      msg.validate[Command] match {
        case JsSuccess(Command.Track(keyword), _) =>
          tracker ! Register(keyword, channel)
        case JsSuccess(Command.Untrack(keyword), _) =>
          tracker ! Unregister(keyword, channel)
        case JsError(error) =>
          channel push Json.toJson(Error.BadRequest(JsError.toFlatJson(error)))
      }
    }.map { _ =>
      tracker ! UnregisterFromAll(channel)
    }

    (in, out)
  }

}
