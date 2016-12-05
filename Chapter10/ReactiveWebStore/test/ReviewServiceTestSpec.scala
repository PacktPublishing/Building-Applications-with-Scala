import org.scalatestplus.play._
import scala.collection.mutable
import services.IReviewService
import services.ReviewService
import mocks.ReviewMockedDao
import utils.Awaits

class ReviewServiceTestSpec extends PlaySpec {
    
      "ReviewService" must {
        
        val service:IReviewService = new ReviewService(new ReviewMockedDao)
      
        "insert a review properly" in {
           val review = new models.Review(Some(1),Some(1),"diegopacheco","Testing is Cool")
           service.insert(review)
        }
        
        "update a reviewt" in {
           val review = new models.Review(Some(1),Some(1),"diegopacheco","Testing so so Cool")
           service.update(1, review)
        }
        
        "not update because does not exist" in {
          intercept[RuntimeException]{
             Awaits.get(5, service.update(333,null))
          }
        }
        
        "find the review 1" in {
           val review =  Awaits.get(5,service.findById(1))
           review.get.id mustBe Some(1)
           review.get.author mustBe "diegopacheco"
           review.get.comment mustBe "Testing so so Cool"
           review.get.productId mustBe Some(1)
        }
        
        "find all" in {
          val reviews = Awaits.get(5,service.findAll())
          reviews.get.length mustBe 1
          reviews.get(0).id mustBe Some(1)
          reviews.get(0).author mustBe "diegopacheco"
          reviews.get(0).comment mustBe "Testing so so Cool"
          reviews.get(0).productId mustBe Some(1)
        }
        
        "remove 1 review" in {
          val review = Awaits.get(5, service.remove(1))
          review mustBe 1
          
          val oldReview= Awaits.get(5, service.findById(1))
          oldReview mustBe None
        }
        
        "not remove because does not exist" in {
          intercept[RuntimeException]{
             Awaits.get(5, service.remove(-1))
          }
        }
        
  }
 
  
}