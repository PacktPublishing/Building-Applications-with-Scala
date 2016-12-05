package proxy

import client.WSFactory
import utils.Awaits

case class Image 
 (var id:Option[Long],
  var productId:Option[Long],
  var url:String)
{
  override def toString:String = {
    "Image { productId: " + productId.getOrElse(0) + ",url: " + url + "}" 
  }
}

object ImagesJson {
  
   import play.api.libs.json._
   import play.api.libs.json.Reads._
   import play.api.libs.functional.syntax._
  
   implicit val imagesWrites: Writes[Image] = (
    (JsPath \ "id").write[Option[Long]] and
    (JsPath \ "productId").write[Option[Long]] and
    (JsPath \ "url").write[String]
    )(unlift(Image.unapply))

   implicit val imagesReads: Reads[Image] = (
    (JsPath \ "id").readNullable[Long] and
    (JsPath \ "productId").readNullable[Long] and 
    (JsPath \ "url").read[String]
    )(Image.apply _)
   
   def toJson(images:Option[Seq[Image]]) = Json.toJson(images)  
    
}

object ImageProxy {
    
    import scala.concurrent.Future
    import play.api.libs.json._
    import ImagesJson._
    
    val url = "http://localhost:9000/REST/api/image/all"
    implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext
    
    def listAll():Option[List[Image]] = {
        val ws  = WSFactory.ws
        val futureResult:Future[Option[List[Image]]] = ws.url(url).withHeaders("Accept" -> "application/json").get().map(
            response => Json.parse(response.body).validate[List[Image]].asOpt
        )
        val images = Awaits.get(10, futureResult)
        ws.close
        images
    }
  
}