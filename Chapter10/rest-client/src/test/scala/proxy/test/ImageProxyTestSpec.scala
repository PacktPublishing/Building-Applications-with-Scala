package proxy.test

import org.scalatest._
import proxy.ImageProxy
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import java.util.concurrent.CountDownLatch

class ImageProxyTestSpec extends FlatSpec with Matchers {

  "A Image REST Proxy " should "return all images" in {
      val images = ImageProxy.listAll().get
      images shouldNot(be(null))
      images shouldNot(be(empty))
      
      for( i <- images){
         println(i)
      }
  }
  
  "A Image REST Proxy " should "suffer backpressure" in {
      val latch = new CountDownLatch(10)
      var errorCount:Int = 0
      for(i <- 1 to 10){
          Future{
              try{
                  val images = ImageProxy.listAll().get
                  images shouldNot(be(null))
                  for( i <- images){
                     println(i)
                  }                
              }catch{
                case t:Throwable => errorCount += 1
              }
              latch.countDown()
          }
      }
      while( latch.getCount >= 1 )
        latch.await()
        
      errorCount should be >=5     
  }

}