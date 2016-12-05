import org.scalatestplus.play.PlaySpec

import mocks.ImageMockedDao
import services.IImageService
import services.ImageService
import utils.Awaits

class ImageServiceTestSpec extends PlaySpec {
    
      "ImageService" must {
        
        val service:IImageService = new ImageService(new ImageMockedDao)
      
        "insert a image properly" in {
           val image = new models.Image(Some(1),Some(1),"http://www.google.com.br/myimage")
           service.insert(image)
        }
        
        "update a image" in {
           val image = new models.Image(Some(2),Some(1),"http://www.google.com.br/myimage")
           service.update(1, image)
        }
        
        "not update because does not exist" in {
          intercept[RuntimeException]{
             Awaits.get(5, service.update(333,null))
          }
        }
        
        "find the image" in {
           val image = Awaits.get(5,service.findById(1))
           image.get.id mustBe Some(1)
           image.get.productId mustBe Some(1)
           image.get.url mustBe "http://www.google.com.br/myimage"
        }
        
        "find all" in {
          val reviews = Awaits.get(5,service.findAll())
          reviews.get.length mustBe 1
          reviews.get(0).id mustBe Some(1)
          reviews.get(0).productId mustBe Some(1)
          reviews.get(0).url mustBe "http://www.google.com.br/myimage"
        }
        
        "remove 1 image" in {
          val image = Awaits.get(5,service.remove(1))
          image mustBe 1
          
          val oldImage = Awaits.get(5,service.findById(1))
          oldImage mustBe None
        }
        
        "not remove because does not exist" in {
          intercept[RuntimeException]{
             Awaits.get(5,service.remove(-1))
          }
        }
        
    }
  
}