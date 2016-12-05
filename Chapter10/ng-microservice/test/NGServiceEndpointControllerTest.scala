import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.HtmlUnitFactory
import org.scalatestplus.play.OneServerPerSuite
import play.api.libs.ws.WSClient
import play.api.inject.guice.GuiceApplicationBuilder
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration._

class NGServiceEndpointControllerTest extends PlaySpec with OneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
  
     val injector = new GuiceApplicationBuilder()
     .injector 
     val ws:WSClient = injector.instanceOf(classOf[WSClient])
     
     import play.api.libs.concurrent.Execution.Implicits.defaultContext
     
     "NGServiceEndpointController" must {
       "return a sngle double" in { 
          val future =   ws.url(s"http://localhost:${port}/double").get().map { res => res.body }
          val response = Await.result(future, 15.seconds)
          assert( !"".equals(response) )
          assert( new java.lang.Double(response) >= 1 )
       }
      "return a list of 3 doubles" in { 
          val future =   ws.url(s"http://localhost:${port}/doubles/3").get().map { res => res.body }
          val response = Await.result(future, 15.seconds)
          assert( !"".equals(response) )
          assert( response.contains("[") )
          assert( response.contains("]") )
       }       
     }
     
}