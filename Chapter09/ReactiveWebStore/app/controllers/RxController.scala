package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data._
import rx.lang.scala.Observable
import rx.lang.scala.subjects.PublishSubject
import rx.lang.scala.ImplicitFunctionConversions
import rx.lang.scala.schedulers._
import play.api.libs.iteratee.Iteratee
import play.api.libs.iteratee.Enumerator
import scala.concurrent._

import services.IPriceSerice
import rx.lang.scala.Subscription

@Singleton
class RxController @Inject()(priceService:IPriceSerice) extends Controller {
  
  def prices = Action { implicit request =>
     Logger.info("RX called. ")
     
     import ExecutionContext.Implicits.global
     
     val sourceObservable = priceService.generatePrices
     
     val rxResult = Observable.create { sourceObservable.subscribe }
         .subscribeOn(IOScheduler())
         .take(1)
         .flatMap { x => println(x) ; Observable.just(x) }
         .toBlocking
         .first
     
     Ok("RxScala Price sugested is = " + rxResult)
  }
  
  def pricesAsync = Action.async { implicit request => 
     Logger.info("RX Async called. ")
     
     import play.api.libs.concurrent.Execution.Implicits.defaultContext
     
     val sourceObservable = priceService.generatePrices
     
     val rxResult = Observable.create { sourceObservable.subscribe }
         .subscribeOn(IOScheduler())
         .take(1)
         .flatMap { x => println(x) ; Observable.just(x) }
         .toBlocking
         .first
     
     Future { Ok("RxScala Price sugested is = " + rxResult) }
  }
  
}