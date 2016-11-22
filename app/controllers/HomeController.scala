package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.concurrent.Future

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import javax.inject._
import play.api._
import play.api.mvc._

import models.Users
import dao.UserDao

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(userDao: UserDao) extends Controller {
	var userPass: String = ""
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action.async { implicit request =>

  	userDao.findById(1) flatMap {
  		case Some(user) => Future.successful(Ok("user is: "+user.username+" pass is "+user.password))
  		case None => Future.successful(Ok(views.html.index("Your new application is ready.")))
  	}
  }

}
