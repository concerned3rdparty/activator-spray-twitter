package web

import akka.actor._
import akka.io.IO
import spray.can.Http
import spray.can.server.websockets.Sockets

object Main extends App {

  implicit val system = ActorSystem("spray-twitter")

  val port = sys.env.get("PORT") map(_.toInt) getOrElse 8080

  val server = system.actorOf(
    WebSocketServer.props,
    "web-socket-server")

    IO(Sockets) ! Http.Bind(
      listener = server,
      interface = "0.0.0.0",
      port = port)
}
