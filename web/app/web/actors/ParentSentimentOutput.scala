package web.actors

import akka.actor.Actor
import core._

trait ParentSentimentOutput extends SentimentOutput { actor: Actor =>
  def outputCount(values: List[Iterable[(Category, Int)]]): Unit =
    actor.context.parent ! TrackWorker.Publish(values)
}
