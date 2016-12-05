package models

trait BaseModel {
  def getId:Option[Long]
  def setId(id:Option[Long]):Unit
}