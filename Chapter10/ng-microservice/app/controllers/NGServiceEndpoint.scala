package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.libs.json.Json
import play.api.libs.json._

import services.NGContract

class NGServiceEndpoint @Inject()(service:NGContract) extends Controller {
      
  def double = Action {
    Ok(service.generateDouble.toString())  
  }
  
  def doubles(n:Int) = Action {
    val json = Json.toJson(service.generateDoubleBatch(n))
    Ok(json)
  }
  
}