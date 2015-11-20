package controllers

import javax.inject.Inject

import com.sun.xml.internal.ws.addressing.model.ActionNotSupportedException
import play.api.Logger
import play.api.mvc._
import play.api.i18n.{I18nSupport, MessagesApi}

import scala.concurrent.{ExecutionContext, Future}

class Application @Inject()(val messagesApi: MessagesApi)
                           (implicit executionContext: ExecutionContext) extends Controller with I18nSupport {
  val logger = Logger(this.getClass)

  /**
    * Index page.
    *
    * @return
    */
  def index = Action.async { implicit request =>
    Future.successful(Ok(views.html.pager("Pager | On-Demand House Call Doctors Within 2 Hours")))
  }

  /**
    * Services page.
    *
    * @return
    */
  def service = Action.async { implicit request =>
    Future.successful(Ok(views.html.services("Get High-Quality Medical Care On-Demand in NYC &amp; SF | Pager")))
  }

  /**
    * Doctors page.
    *
    * @return
    */
  def doctors = Action.async { implicit request =>
    Future.successful(Ok(views.html.doctors("Board-Certified House Call Doctors Servicing NYC & SF | Pager")))
  }

  /**
    * Pricing page.
    *
    * @return
    */
  def pricing = Action.async { implicit request =>
    Future.successful(Ok(views.html.pricing("Pricing, Billing, and Insurance Info for Our Services | Pager")))
  }

  /**
    * For_Business page
    *
    * @return
    */
  def forBusiness = Action.async { implicit request =>
    Future.successful(Ok(views.html.for_business("Pager For Your Business | Pager")))
  }

  def healthCheckScreening = Action.async { implicit request =>
    Future.successful(Ok(views.html.health_check_screening("Pager Health Check: Nurse Performed Health Screening | Pager")))
  }

  /**
    * Term page.
    *
    * @return
    */
  def term = Action.async { implicit request =>
    Future.successful(Ok(views.html.term("Pager | Terms and Conditions")))
  }

  /**
    * Privacy page.
    *
    * @return
    */
  def privacy = Action.async { implicit request =>
    Future.successful(Ok(views.html.privacy("Pager | Privacy Policy")))
  }

  /**
    * About page.
    *
    * @return
    */
  def about = Action.async { implicit request =>
    Future.successful(Ok(views.html.about("Learn About Pager | On-Demand House Call Doctors")))
  }

  /**
    * Support page.
    *
    * @return
    */
  def support = Action.async { implicit request =>
    Future.successful(Ok(views.html.support("Frequently Asked Questions and Contact information | Pager")))
  }

  def login = Action.async {  implicit request =>
    Future.successful(Ok(views.html.login("Pager | Schedule A Doctor House Call Online")))
  }
}
