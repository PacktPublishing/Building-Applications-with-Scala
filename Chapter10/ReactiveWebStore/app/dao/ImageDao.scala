package dao

import scala.concurrent.Future
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import slick.jdbc.GetResult
import models.Image

trait IImageDao extends BaseDao[Image]{
  def findAll(): Future[Seq[Image]]
  def findById(id:Long): Future[Option[Image]]
  def remove(id:Long): Future[Int]
  def insert(p:Image): Future[Unit]
  def update(p2:Image): Future[Unit] 
}

class ImageDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with IImageDao {
  
  import driver.api._
  
  class ImageTable(tag: Tag) extends Table[Image](tag, models.ImageDef.toTable) {
    def id           = column[Option[Long]]("ID", O.PrimaryKey)
    def productId    = column[Option[Long]]("PRODUCT_ID")
    def url          = column[String]("URL")
    def * = (id, productId, url) <> (Image.tupled, Image.unapply _)
  }
  
  override def toTable = TableQuery[ImageTable]

  private val Images = toTable()
  
  override def findAll(): Future[Seq[Image]] = db.run(Images.result)
  
  override def findById(id:Long): Future[Option[Image]] = db.run(Images.filter( _.id === id).result.headOption)
  
  override def remove(id:Long): Future[Int] = db.run(Images.filter( _.id === id).delete)
  
  override def insert(i:Image): Future[Unit] = db.run(Images += i).map { _ => () }
  
  override def update(i2:Image) = Future[Unit] { 
    db.run(
       Images.filter(_.id === i2.id)
         .map(i => (i.productId,i.url))
         .update((i2.productId,i2.url))
    )
  }
  
}