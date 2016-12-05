import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.HtmlUnitFactory
import org.scalatestplus.play.OneServerPerSuite
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration._
import play.api.libs.ws.WSClient
import javax.inject.Inject
import play.api.inject.guice.GuiceInjectorBuilder
import play.api.inject.Injector
import play.inject.Bindings._
import play.api.inject.guice.GuiceApplicationBuilder
import play.Configuration
import play.Play

class RndDoubleGeneratorControllerTestSpec extends PlaySpec with OneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
     
     val injector = new GuiceApplicationBuilder()
     .injector 
     val ws:WSClient = injector.instanceOf(classOf[WSClient])
     
     import play.api.libs.concurrent.Execution.Implicits.defaultContext 
  
     "Assuming ng-microservice is down rx number should be" must {
       "work" in { 
          val future =   ws.url(s"http://localhost:${port}/rnd/rxbat").get().map { res => res.body }
          val response = Await.result(future, 15.seconds)

          response mustBe "2.3000000000000007"
       }
     }
}