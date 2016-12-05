package backpresurre

import scala.concurrent.duration._
import java.util.Date

class LeakyBucket(var rate: Int, var perDuration: FiniteDuration) {

  var numDropsInBucket: Int = 0
  var timeOfLastDropLeak:Date = null
  var msDropLeaks = perDuration.toMillis

  def dropToBucket():Boolean = {
    synchronized {
      var now = new Date()
      if (timeOfLastDropLeak != null) {
        var deltaT = now.getTime() - timeOfLastDropLeak.getTime()
        var numberToLeak:Long =  deltaT / msDropLeaks 
        
        if (numberToLeak > 0) { 
            if (numDropsInBucket <= numberToLeak) {
              numDropsInBucket -= numberToLeak.toInt
            } else {
              numDropsInBucket = 0
            }
            timeOfLastDropLeak = now
        }
      }else{
         timeOfLastDropLeak = now
      }
      if (numDropsInBucket < rate) {
        numDropsInBucket = numDropsInBucket + 1
        return true; 
      }
      return false;
    }
  }

}
