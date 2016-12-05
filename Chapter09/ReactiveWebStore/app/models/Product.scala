package models

case class Product 
 ( var id:Option[Long],
   var name:String,
   var details:String,
   var price:BigDecimal ) 
extends BaseModel{
  override def toString:String = {
    "Product { id: " + id.getOrElse(0) + ",name: " + name + ", details: "+ details + ", price: " + price  + "}" 
  }
  override def getId:Option[Long] = id
  override def setId(id:Option[Long]):Unit = this.id = id
}

object ProductDef{
  def toTable:String = "Product"
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