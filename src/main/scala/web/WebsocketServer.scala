package web

import akka.actor._
import spray.can.Http
import spray.can.server.websockets._
import spray.http._
import HttpMethods._

class WebSocketServer extends Actor {

  def actorRefFactory = context

  var rooms = Map.empty[String, ActorRef]

  def receive = {
    case _: Http.Connected =>
      sender ! Http.Register(self)

    case r @ HttpRequest(GET, Uri.Path(path), _, _, _) =>
      val q = "test"
      sender ! Sockets.UpgradeServer(
        Sockets.acceptAllFunction(r),
        getWebsocketWorker(q))
  }

  private def getWebsocketWorker(q: String): ActorRef =
    context.child(q) getOrElse context.actorOf(WebsocketWorker.props(q), q)

}

object WebSocketServer {
  def props = Props[WebSocketServer]
}
