package services

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.Random.nextDouble
import javax.inject._
import play.api.libs.json._
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import rx.lang.scala.Observable
import rx.lang.scala.Observer
import rx.lang.scala.Subscription
import rx.lang.scala.schedulers._
import rx.lang.scala.subjects.PublishSubject
import scala.util.Success
import scala.util.Failure

trait IRndService {
   def next():Double
   def call():Future[String]
   def rxScalaCall():Observable[Double]
   def rxScalaCallBatch():Observable[Double]
}

@Singleton
class RndService @Inject() (ws: WSClient) extends IRndService {
    
    import play.api.libs.concurrent.Execution.Implicits.defaultContext
  
    override def next():Double = {
       val future = ws.url("http://localhost:9090/double").get().map { res => res.body.toDouble }
       Await.result(future, 5.seconds)
    }
    
    override def call():Future[String] = {
        ws.url("http://localhost:9090/double").get().map { res => res.body }
    }
    
    override def rxScalaCall():Observable[Double] = {
        val doubleFuture:Future[Double] = ws.url("http://localhost:9090/double").get().map { x => x.body.toDouble }
        Observable.from(doubleFuture)
    }
    
    override def rxScalaCallBatch():Observable[Double] = {
        
        var doubleInfiniteStreamSubject = PublishSubject.apply[Double]()
        
        val future = ws.url("http://localhost:9090/doubles/10")
                       .get()
                       .map { x => Json.parse(x.body).as[List[Double]] }
          
        future.onComplete {
            case Success(l:List[Double]) =>  l.foreach { e => doubleInfiniteStreamSubject.onNext(e) }
            case Failure(e:Exception)    =>  doubleInfiniteStreamSubject.onError(e) 
        } 
        
        var observableEven = Observable.create { doubleInfiniteStreamSubject.subscribe }
          //.subscribeOn(IOScheduler())
          .onErrorReturn { x => 2.0 }
          .flatMap { x => Observable.from( Iterable.fill(1)(x + 10) ) }
          .filter { x => x.toInt % 2 == 0 }
          .flatMap { x => println("ODD: " + x) ; Observable.just(x) }
          
        var observableOdd = Observable.create { doubleInfiniteStreamSubject.subscribe }
          //.subscribeOn(IOScheduler())
          .onErrorReturn { x => 1.0 }
          .flatMap { x => Observable.from( Iterable.fill(1)(x + 10) ) }
          .filter { x => x.toInt % 2 != 0 }
          .flatMap { x => println("EVEN: " + x) ; Observable.just(x) }
        
        var mergeObservable = Observable
           .empty
           //.subscribeOn(IOScheduler())       
           .merge(observableEven)
           .merge(observableOdd)
           .take(10)
           .foldLeft(0.0)(_+_)
           .flatMap { x =>  Observable.just( x - (x * 0.9) ) }
        
        mergeObservable
    }
    
}
