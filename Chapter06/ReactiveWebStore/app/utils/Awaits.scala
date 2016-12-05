package utils

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.Await

/**
 * Utility implementation to get Results out of Futures.
 */
object Awaits {
    
  def get[T](sec:Int,f:Future[T]):T = {
     Await.result[T](f, sec seconds)    
  }
  
}