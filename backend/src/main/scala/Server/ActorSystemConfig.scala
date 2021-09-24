package Server

import akka.actor.{ActorSystem, Scheduler}
import scala.concurrent.ExecutionContext

trait ActorSystemConfig {
  implicit val system: ActorSystem = ActorSystem("Atlas")
  implicit val executionContext: ExecutionContext = system.dispatcher
  implicit val scheduler: Scheduler = system.scheduler
}
