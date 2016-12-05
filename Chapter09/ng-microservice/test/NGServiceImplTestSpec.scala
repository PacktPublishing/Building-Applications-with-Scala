import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

import services.NGContract
import services.NGServiceImpl

class NGServiceImplTestSpec extends PlaySpec {
  
  "The NGServiceImpl" must {
    
    "Generate a Ramdon number" in {
        val service:NGContract = new NGServiceImpl
        val double = service.generateDouble
        assert( double >= 1 )
    }
    
    "Generate a list of 3 Ramdon numbers" in {
        val service:NGContract = new NGServiceImpl
        val doubles = service.generateDoubleBatch(3)
        doubles.size mustBe 3
        assert( doubles(0) >= 1 )
        assert( doubles(1) >= 1 )
        assert( doubles(2) >= 1 )
    }
    
  }
  
}