package services

trait NGContract {
  def generateDouble:Double
  def generateDoubleBatch(n:Int):List[Double]
}