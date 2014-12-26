package web

import akka.actor._
import akka.util.ByteString
import akka.io.Tcp._
import spray.can.server.websockets._
import spray.can.server.websockets.model._
import spray.can.server.websockets.model.OpCode._
import spray.json._
import DefaultJsonProtocol._
import core._

class WebsocketWorker(query: String) extends Actor {

  trait SelfSentimentOutput extends SentimentOutput {
    def outputCount(values: List[Iterable[(Category, Int)]]): Unit =
      self ! WebsocketWorker.Output(values)
  }

  val sentiment = context.actorOf(Props(new SentimentAnalysisActor with CSVLoadedSentimentSets with SelfSentimentOutput))
  val stream = context.actorOf(Props(new TweetStreamerActor(TweetStreamerActor.twitterUri, sentiment) with OAuthTwitterAuthorization))
  stream ! query

  def receive = {
    case Sockets.Upgraded =>
      register(sender)

    case msg: ConnectionClosed =>
      unregister(sender)

    case WebsocketWorker.Output(values) =>
      broadcast(format(values))
  }

  var clients = Set.empty[ActorRef]

  def register(client: ActorRef) =
    clients += client

  def unregister(client: ActorRef) =
    clients -= client

  def format(values: List[Iterable[(String, Int)]]): Frame =
    Frame(opcode = Text, data = ByteString(values.toJson.compactPrint))

  def broadcast(frame: Frame) =
    clients.foreach(_ ! frame)
}

object WebsocketWorker {
  case class Output(values: List[Iterable[(String, Int)]])

  def props(query: String) = Props(new WebsocketWorker(query))
}
