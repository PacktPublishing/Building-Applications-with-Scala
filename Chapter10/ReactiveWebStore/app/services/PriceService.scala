package services


import javax.inject._

import rx.lang.scala.Observable
import rx.lang.scala.subjects.PublishSubject
import rx.lang.scala.ImplicitFunctionConversions
import rx.lang.scala.schedulers._

import scala.concurrent.Future
import scala.concurrent._
import ExecutionContext.Implicits.global

import scala.util.Random.nextDouble

trait IPriceSerice{
  def generatePrices:Observable[Double]
}

@Singleton
class PriceService extends IPriceSerice{
    
  var doubleInfiniteStreamSubject = PublishSubject.apply[Double]()
  
  Future { 
    Stream.continually(nextDouble * 1000.0 ).foreach { 
      x => doubleInfiniteStreamSubject.onNext(x) 
    } 
  }
  
  override def generatePrices:Observable[Double] = {
    
    var observableEven = Observable.create { doubleInfiniteStreamSubject.subscribe }
      .subscribeOn(IOScheduler())
      .flatMap { x => Observable.from( Iterable.fill(1)(x + 10) ) }
      .filter { x => x.toInt % 2 == 0 }
      
    var observableOdd = Observable.create { doubleInfiniteStreamSubject.subscribe }
      .subscribeOn(IOScheduler())
      .flatMap { x => Observable.from( Iterable.fill(1)(x + 10) ) }
      .filter { x => x.toInt % 2 != 0 }
    
    var mergeObservable = Observable
       .empty
       .subscribeOn(IOScheduler())       
       .merge(observableEven)
       .merge(observableOdd)
       .take(10)
       .foldLeft(0.0)(_+_)
       .flatMap { x =>  Observable.just( x - (x * 0.9) ) }
    
    return mergeObservable   
  }
  
}