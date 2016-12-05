package services

import scala.concurrent.Future

trait BaseService[A] {
  def insert(a:A):Future[Unit]
  def update(id:Long,a:A):Future[Unit]
  def remove(id:Long):Future[Int]
  def findById(id:Long):Future[Option[A]]
  def findAll():Future[Option[Seq[A]]]
}
