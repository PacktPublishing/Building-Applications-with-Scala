import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.HtmlUnitFactory
import org.scalatestplus.play.OneServerPerSuite
import utils.DBCleaner
 
class ImageControllerTestSpec  extends PlaySpec with OneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
    
    DBCleaner.cleanUp()
  
    "ImageController" should {
      "insert a new image should be ok" in {
            goTo(s"http://localhost:${port}/product/add")
            click on id("name")
            enter("Blue Ball")
            click on id("details")
            enter("Blue Ball is a Awesome and simple product")
            click on id("price")
            enter("17.55")
            submit()
            
            goTo(s"http://localhost:${port}/image/add")
            singleSel("productId").value = "1"
            click on id("url")
            enter("https://thegoalisthering.files.wordpress.com/2012/01/bluetennisball_display_image.jpg")
            submit()
            
      }
      
      "details from the image 1 should be ok" in {
            goTo(s"http://localhost:${port}/image/details/1")
            textField("url").value mustBe "https://thegoalisthering.files.wordpress.com/2012/01/bluetennisball_display_image.jpg"
      }
      
      "update image 1 should be ok" in {
            goTo(s"http://localhost:${port}/image/details/1")
            textField("url").value = "https://thegoalisthering.files.wordpress.com/2012/01/bluetennisball_display_image2.jpg"
            submit()
            
            goTo(s"http://localhost:${port}/image/details/1")
            textField("url").value mustBe "https://thegoalisthering.files.wordpress.com/2012/01/bluetennisball_display_image2.jpg"
      }
      
      "delete a image should be ok" in {
            goTo(s"http://localhost:${port}/image/add")    
            singleSel("productId").value = "1"
            click on id("url")
            enter("https://thegoalisthering.files.wordpress.com/2012/01/bluetennisball_display_image.jpg")
            submit()
            
            goTo(s"http://localhost:${port}/image")
            click on id("btnDelete")
      }
      
      "Cleanup db in the end" in {
          DBCleaner.cleanUp()
      }
      
  }
 
  
}