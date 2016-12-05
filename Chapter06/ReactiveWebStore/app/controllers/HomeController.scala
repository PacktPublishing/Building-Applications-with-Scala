package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class HomeController @Inject() extends Controller {
  
  def index = Action {
    Ok(views.html.index("Welcome to ReactiveWebStore")(Flash(Map("success" -> "Welcome!"))))
  }
  
}