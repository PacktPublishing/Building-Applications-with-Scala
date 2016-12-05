import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

class LeakyBucketTestSpec extends PlaySpec {
    
   "LeakyBucket" should {

    "Make the RATE per Second always right" in  {
      
        import java.util.concurrent.CountDownLatch
        import scala.concurrent.ExecutionContext.Implicits.global
        import scala.concurrent.Future
        import backpresurre.LeakyBucket
        import scala.concurrent.duration._
        
        val rate = 150
        var bucket = new LeakyBucket(rate, 1 second)
        testBucket(150,rate)
        testBucket(300,rate)
        testBucket(150,rate)
        testBucket(600,rate)
        testBucket(150,rate)
        testBucket(50,rate)
        
        def testBucket(size:Int,factor:Int):Unit = {
            var okCount = 0
            var koCount = 0
            var testSize = size
            var latch:CountDownLatch = new CountDownLatch(testSize)
            
            for(i <- 1 to testSize){
               Future{
                   if (bucket.dropToBucket()) {
                      print("K, ")
                      okCount += 1
                  }else{
                     print("E, ")
                     koCount += 1
                  }
                  latch.countDown()
               }
            }
            
            while(0 != latch.getCount)
              latch.await()
              
            println("DONE")
            println("OK  :  " + okCount)
            println("ERR :  " + koCount)
            
            val ok = if(size<=factor) size else factor
            val ko = if ( (size-factor) >=0 ) (size-factor) else 0
            
            okCount mustBe ok
            koCount mustBe ko 
            
            Thread.sleep(2000)
        }
    }

  }
  
}