package dao

import slick.lifted.TableQuery
import scala.concurrent.Future

/**
 * Defines base dao structure every dao should have.
 */
trait BaseDao[T <: Any] {
  
  def toTable():TableQuery[_]
  
  def findAll():Future[Seq[T]]
  
  def remove(id:Long):Future[Int]
  
  def insert(o:T):Future[Unit]
  
  def update(o:T):Future[Unit]
  
  def findById(id:Long):Future[Option[T]]
  
}