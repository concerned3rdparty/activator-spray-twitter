package web

import java.util.concurrent.atomic.AtomicReference
import akka.actor.{ActorSystem, ActorRef}
import play.api._

object Global extends GlobalSettings {

  private[this] val system_ = new AtomicReference[ActorSystem]()
  private[this] val tracker_ = new AtomicReference[ActorRef]

  def tracker = tracker_.get
  def system = system_.get

  override def onStart(app: Application) {
    val system = ActorSystem("twitter-tracker")
    system_.set(system)

    tracker_.set(system.actorOf(web.actors.Tracker.props))
  }

  override def onStop(app: Application) {
    system.shutdown()
  }
}
