package filters

import javax.inject.{Provider, Inject}

import play.api.mvc.Results._
import play.api.mvc.{Result, RequestHeader}
import play.api.routing.Router
import play.api.{UsefulException, OptionalSourceMapper, Environment, Configuration}
import play.api.http.DefaultHttpErrorHandler

import scala.concurrent.Future

/**
 * Global http error handler.
 *
 * @note extends DefaultHttpErrorHandler.
 *
 */
class GlobalHttpErrorHandler @Inject()(env: Environment,
                               config: Configuration,
                               sourceMapper: OptionalSourceMapper,
                               router: Provider[Router]) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  override protected def onOtherClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = super.onOtherClientError(request, statusCode, message)

  override protected def logServerError(request: RequestHeader, usefulException: UsefulException): Unit = super.logServerError(request, usefulException)

  override protected def onDevServerError(request: RequestHeader, exception: UsefulException): Future[Result] = super.onDevServerError(request, exception)

  override protected def onProdServerError(request: RequestHeader, exception: UsefulException): Future[Result] = super.onProdServerError(request, exception)

  override protected def onNotFound(request: RequestHeader, message: String): Future[Result] = super.onNotFound(request, message)

  override protected def onBadRequest(request: RequestHeader, message: String): Future[Result] = super.onBadRequest(request, message)

  override protected def onForbidden(request: RequestHeader, message: String): Future[Result] = {
    Future.successful {
      Forbidden("You're not allowed to access this resource.")
    }
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful {
      InternalServerError("A server error occurred: " + exception.getMessage)
    }
  }

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful {
      Status(statusCode)(s"A client error occurred: $message, with statusCode: $statusCode")
    }
  }
}
