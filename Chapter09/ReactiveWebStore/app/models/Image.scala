package models

case class Image 
 (var id:Option[Long],
  var productId:Option[Long],
  var url:String)
extends BaseModel {
  override def toString:String = {
    "Image { productId: " + productId.getOrElse(0) + ",url: " + url + "}" 
  }
  override def getId:Option[Long] = id
  override def setId(id:Option[Long]):Unit = this.id = id
}

object ImageDef{
  def toTable:String = "Image"
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