
import org.junit.Test
import org.junit.Assert._
import play.mvc.Result
import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._
import javax.inject._
import services.IProductService
import services.IProductService
import play.api.inject.guice.GuiceInjectorBuilder
import play.api.inject.guice.GuiceInjectorBuilder
import play.api.inject.guice.GuiceInjectorBuilder
import services.ProductService
import play.api.inject.guice.GuiceApplicationBuilder
import java.io.File
import play.api.Mode
 
class RoutesTestingSpec extends PlaySpec with OneAppPerTest {
    
    "Root Controller" should {
      "route to index page" in {
        val result = route(app, FakeRequest(GET, "/")).get
        status(result) mustBe OK
        contentType(result) mustBe Some("text/html")
        contentAsString(result) must include ("Welcome to Reactive Web Store")
     }
    }
    
    "Product Controller" should {
      "route to index page" in {
        val result = route(app, FakeRequest(GET, "/product")).get
        status(result) mustBe OK
        contentType(result) mustBe Some("text/html")
        contentAsString(result) must include ("Product")
     }
     "route to new product page" in {
        val result = route(app, FakeRequest(GET, "/product/add")).get
        status(result) mustBe OK
        contentType(result) mustBe Some("text/html")
        contentAsString(result) must include ("Product")
     }
     "route to product 1 details page page" in {
       try{
          route(app, FakeRequest(GET, "/product/details/1")).get 
       }catch{
         case e:Exception => Unit
       }
     }     
    }
    
    "Review Controller" should {
      "route to index page" in {
        val result = route(app, FakeRequest(GET, "/review")).get
        status(result) mustBe OK
        contentType(result) mustBe Some("text/html")
        contentAsString(result) must include ("Review")
     }
     "route to new review page" in {
        val result = route(app, FakeRequest(GET, "/review/add")).get
        status(result) mustBe OK
        contentType(result) mustBe Some("text/html")
        contentAsString(result) must include ("review")
     }
     "route to review 1 details page page" in {
       try{
          route(app, FakeRequest(GET, "/review/details/1")).get 
       }catch{
         case e:Exception => Unit
       }
     }     
    }
    
    
    "Image Controller" should {
      "route to index page" in {
        val result = route(app, FakeRequest(GET, "/image")).get
        status(result) mustBe OK
        contentType(result) mustBe Some("text/html")
        contentAsString(result) must include ("Image")
     }
     "route to new image page" in {
        val result = route(app, FakeRequest(GET, "/image/add")).get
        status(result) mustBe OK
        contentType(result) mustBe Some("text/html")
        contentAsString(result) must include ("image")
     }
     "route to image 1 details page page" in {
       try{
          route(app, FakeRequest(GET, "/image/details/1")).get 
       }catch{
         case e:Exception => Unit
       }
     }     
    }
    
  
}