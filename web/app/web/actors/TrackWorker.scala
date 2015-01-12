package web.actors

import akka.actor._
import play.api.libs.json._
import play.api.libs.iteratee.Concurrent.Channel
import core._

class TrackWorker(keyword: String) extends Actor with ActorLogging {
  import TrackWorker._

  val sentiment = context.actorOf(Props(new SentimentAnalysisActor with CSVLoadedSentimentSets with ParentSentimentOutput))
  val stream = context.actorOf(Props(new TweetStreamerActor(TweetStreamerActor.twitterUri, sentiment) with OAuthTwitterAuthorization))

  stream ! keyword

  var channels = Set.empty[Channel[JsValue]]

  def receive = {
    case Register(channel) =>
      channels += channel
    case Unregister(channel) =>
      channels -= channel
    case Publish(values) =>
      broadcast(format(values))
  }

  override def unhandled(msg: Any) = {
    log.warning(s"TrackWorker({}) ignored unhandled message: {}", keyword, msg)
    super.unhandled(msg)
  }

  def format(values: List[Iterable[(String, Int)]]) =
    Json.obj(
      "keyword" -> keyword,
      "values" -> values.map(_.toMap)
    )

  def broadcast(message: JsObject) =
    channels.foreach(_.push(message))

}

object TrackWorker {
  case class Register(channel: Channel[JsValue])
  case class Unregister(channel: Channel[JsValue])

  case class Publish(values: List[Iterable[(String, Int)]])

  def props(keyword: String) = Props(new TrackWorker(keyword))
}
