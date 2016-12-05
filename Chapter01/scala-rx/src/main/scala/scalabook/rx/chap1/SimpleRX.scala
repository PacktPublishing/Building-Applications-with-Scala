package scalabook.rx.chap1

import rx.lang.scala.Observable
import scala.concurrent.duration._

object SimpleRX extends App {
  
  val o = Observable.
            interval(100 millis).
            take(5)            
            
  o.subscribe( x => println(s"Got it: $x") )            
  Thread.sleep(1000)          
            
  Observable.
      just(1, 2, 3, 4).
      reduce(_+_).
      subscribe( r => println(s"Sum 1,2,3,4 is $r in a Rx Way"))
  
}