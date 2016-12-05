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