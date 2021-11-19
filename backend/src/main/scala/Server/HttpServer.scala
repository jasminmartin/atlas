package Server

import Server.Configuration.appConfig.application
import Server.Configuration.{allRoutes, _}
import akka.http.scaladsl.Http
import org.slf4j.LoggerFactory

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}

object HttpServer extends App {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt(application.interface, application.port).bind(allRoutes.route)

  Try(Await.result(bindingFuture, application.serverSetupTimeout)) match {
    case Success(bind) =>
      logger.info(s"Atlas is running on port: ${application.port}")
      sys.addShutdownHook {
        val hook = for {
          _ <- bind.unbind
          _ <- system.terminate
        } yield logger.info("System terminated")
        Try(Await.result(hook, application.serverSetupTimeout)) match {
          case Success(_) => logger.info(s"Successfully completed shutdown hook")
          case Failure(exception) => logger.error(s"Failed to run shutdown hook with exception: $exception")
        }
      }
    case Failure(exception) =>
      logger.error(s"Failed to bind application exception: $exception")
      system.terminate
      sys.exit(1)
  }
}
