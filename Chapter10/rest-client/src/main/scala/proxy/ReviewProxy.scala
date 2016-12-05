package proxy

import client.WSFactory
import utils.Awaits

case class Review
 (var id:Option[Long],
  var productId:Option[Long],
  var author:String,
  var comment:String)
{
  override def toString:String = {
    "Review { id: " + id + " ,productId: " + productId.getOrElse(0) + ",author: " + author + ",comment: " + comment +  " }" 
  }
}

object ReviewsJson {
  
   import play.api.libs.json._
   import play.api.libs.json.Reads._
   import play.api.libs.functional.syntax._
  
   implicit val reviewWrites: Writes[Review] = (
    (JsPath \ "id").write[Option[Long]] and
    (JsPath \ "productId").write[Option[Long]] and
    (JsPath \ "author").write[String] and
    (JsPath \ "comment").write[String]
    )(unlift(Review.unapply))

   implicit val reviewReads: Reads[Review] = (
    (JsPath \ "id").readNullable[Long] and
    (JsPath \ "productId").readNullable[Long] and 
    (JsPath \ "author").read[String] and
    (JsPath \ "comment").read[String]
    )(Review.apply _)
   
   def toJson(reviews:Option[Seq[Review]]) = Json.toJson(reviews)  
    
}

object ReviewProxy {
    
    import scala.concurrent.Future
    import play.api.libs.json._
    import ReviewsJson._
    
    val url = "http://localhost:9000/REST/api/review/all"
    implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext
    
    def listAll():Option[List[Review]] = {
        val ws  = WSFactory.ws
        val futureResult:Future[Option[List[Review]]] = ws.url(url).withHeaders("Accept" -> "application/json").get().map(
            response => Json.parse(response.body).validate[List[Review]].asOpt
        )
        val reviews = Awaits.get(10, futureResult)
        ws.close 
        reviews
    }

  
}