
import org.scalatestplus.play.PlaySpec
 
class MyFirstPlaySpec extends PlaySpec {
  
    "x + 1 " must {
      "be 2 if x=1 " in {
          val sum = 1 + 1
          sum mustBe 2
      }
      "be 0 if x=-1 " in {
          val sum = -1 + 1
          sum mustBe 0
      }
    }
  
}