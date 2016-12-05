package proxy.test

import org.scalatest._
import proxy.ProductProxy

class ProductProxtTestSpec extends FlatSpec with Matchers {

  "A Product Rest proxy " should "return all products" in {
      val products = ProductProxy.listAll().get
      products shouldNot(be(null))
      products shouldNot(be(empty))
      
      for( p <- products ){
         println(p)
      }
  }

}