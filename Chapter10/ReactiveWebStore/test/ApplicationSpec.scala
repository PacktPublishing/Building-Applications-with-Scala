import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._
import org.scalatest.words.BeWord

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Reactive Web Store")
    }

  }

  "RndController" should {
    "return a random number" in {
      // Assuming ng-microservice is down otherwise will fail.
      contentAsString(route(app, FakeRequest(GET, "/rnd/rxbat")).get) mustBe "2.3000000000000007"
    }
  }

}
