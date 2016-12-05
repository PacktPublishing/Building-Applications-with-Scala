import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.scalatest.junit.AssertionsForJUnit
import org.scalatestplus.play.PlaySpec
import services.ProductService
import mocks.ProductMockedDao
import utils.Awaits

@RunWith(classOf[Suite])
@Suite.SuiteClasses(Array(classOf[JunitSimpleTest]))
class JunitSimpleSuiteTest

class JunitSimpleTest extends PlaySpec with AssertionsForJUnit {
   
  @Test def testBaseService() {
    val s = new ProductService(new ProductMockedDao)
    val result = Awaits.get(5, s.findAll() )
    assertEquals(Some(Stream()), result)
    assertTrue( result != null)
    println("All good junit works fine with ScalaTest and Play")
  }
  
}