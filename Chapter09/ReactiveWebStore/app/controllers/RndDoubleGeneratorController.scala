package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import rx.lang.scala.Observable
import rx.lang.scala.subjects.PublishSubject
import rx.lang.scala.ImplicitFunctionConversions
import rx.lang.scala.schedulers._
import scala.concurrent._
import rx.lang.scala.Subscription
import services.IRndService

@Singleton
class RndDoubleGeneratorController @Inject() (service:IRndService) extends Controller {
    
    import play.api.libs.concurrent.Execution.Implicits.defaultContext
  
    def rndDouble = Action { implicit request =>
       Ok( service.next().toString() )
    }
    
    def rndCall = Action.async { implicit request =>
       service.call().map { res => Ok(res) }
    } 
    
    def rxCall = Action { implicit request =>
       Ok(service.rxScalaCall().toBlocking.first.toString())
    }
    
    def rxScalaCallBatch = Action { implicit request =>
       Ok(service.rxScalaCallBatch().toBlocking.first.toString())
    }
     
    
}