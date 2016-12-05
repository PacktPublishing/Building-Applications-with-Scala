package proxy.test

import org.scalatest._
import proxy.ReviewProxy

class ReviewProxyTestSpec extends FlatSpec with Matchers {

  "A Review REST Proxy " should "return all reviews" in {
      val reviews = ReviewProxy.listAll().get
      reviews shouldNot(be(null))
      reviews shouldNot(be(empty))
      
      for( r <- reviews){
         println(r)
      }
  }

}