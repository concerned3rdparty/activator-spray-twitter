package web.actors

import akka.actor._
import play.api.libs.json._
import play.api.libs.iteratee.Concurrent.Channel
import core._

class TrackWorker(keyword: String) extends Actor with ActorLogging {
  import Tracker._
  import TrackWorker._

  val sentiment = context.actorOf(Props(new SentimentAnalysisActor with CSVLoadedSentimentSets with ParentSentimentOutput))
  val stream = context.actorOf(Props(new TweetStreamerActor(TweetStreamerActor.twitterUri, sentiment) with OAuthTwitterAuthorization))

  stream ! keyword

  var channels = Set.empty[Channel[JsValue]]

  def receive = {
    case Register(`keyword`, channel) =>
      channels += channel
    case Unregister(`keyword`, channel) =>
      channels -= channel
    case Publish(values) =>
      broadcast(format(values))
  }

  override def unhandled(msg: Any) = {
    log.warning(s"TrackWorker({}) ignored unhandled message: {}", keyword, msg)
    super.unhandled(msg)
  }

  def format(values: List[Iterable[(String, Int)]]): JsValue =
    Json.toJson(values.map(_.toMap))

  def broadcast(message: JsValue) =
    channels.foreach(_.push(message))

}

object TrackWorker {
  case class Publish(values: List[Iterable[(String, Int)]])

  def props(keyword: String) = Props(new TrackWorker(keyword))
}
