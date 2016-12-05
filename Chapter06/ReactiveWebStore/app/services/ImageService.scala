package services

import scala.concurrent.Future
import dao.ImageDao
import javax.inject.Inject
import javax.inject.Singleton
import models.Image
import play.api.libs.concurrent.Execution.Implicits
import utils.Awaits
import dao.IImageDao

trait IImageService extends BaseService[Image]{
  def insert(image:Image):Future[Unit]
  def update(id:Long,image:Image):Future[Unit]
  def remove(id:Long):Future[Int]
  def findById(id:Long):Future[Option[Image]]
  def findAll():Future[Option[Seq[Image]]]
}

@Singleton
class ImageService @Inject() (dao:IImageDao) extends IImageService {
  
  import play.api.libs.concurrent.Execution.Implicits.defaultContext
  
  def insert(image:Image):Future[Unit] = {
     dao.insert(image)
  }
  
  def update(id:Long,image:Image):Future[Unit] = {
     image.id =  Option(id.toInt)
     dao.update(image)
  }
  
  def remove(id:Long):Future[Int] = {
     dao.remove(id)
  }
  
  def findById(id:Long):Future[Option[Image]] = {
     dao.findById(id)
  }
  
  def findAll():Future[Option[Seq[Image]]] = {
     dao.findAll().map { x => Option(x) }
  }
  
  private def validateId(id:Long):Unit = {
     val future = findById(id)
     val entry  = Awaits.get(5, future)  
     if (entry==null ||entry.equals(None) ) throw new RuntimeException("Could not find Image: " + id)
  }
  
}