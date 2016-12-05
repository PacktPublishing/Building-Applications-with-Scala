package proxy

import client.WSFactory
import utils.Awaits

case class Product 
 ( var id:Option[Long],
   var name:String,
   var details:String,
   var price:BigDecimal ) {
  override def toString:String = {
    "Product { id: " + id.getOrElse(0) + ",name: " + name + ", details: "+ details + ", price: " + price  + "}" 
  }
}

object ProductsJson {
  
   import play.api.libs.json._
   import play.api.libs.json.Reads._
   import play.api.libs.functional.syntax._
  
   implicit val productWrites: Writes[Product] = (
    (JsPath \ "id").write[Option[Long]] and
    (JsPath \ "name").write[String] and
    (JsPath \ "details").write[String] and
    (JsPath \ "price").write[BigDecimal]
    )(unlift(Product.unapply))

   implicit val productReads: Reads[Product] = (
    (JsPath \ "id").readNullable[Long] and
    (JsPath \ "name").read[String] and 
    (JsPath \ "details").read[String] and
    (JsPath \ "price").read[BigDecimal]
    )(Product.apply _)
   
   def toJson(products:Option[Seq[Product]]) = Json.toJson(products)  
    
}

object ProductProxy {
    
    import scala.concurrent.Future
    import play.api.libs.json._
    import ProductsJson._
    
    val url = "http://localhost:9000/REST/api/product/all"
    implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext
    
    def listAll():Option[List[Product]] = {
        val ws  = WSFactory.ws
        val futureResult:Future[Option[List[Product]]] = ws.url(url).withHeaders("Accept" -> "application/json").get().map(
            response => Json.parse(response.body).validate[List[Product]].asOpt
        )
        val products = Awaits.get(10, futureResult)
        ws.close
        products
    }
  
}