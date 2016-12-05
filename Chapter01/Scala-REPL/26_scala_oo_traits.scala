trait Car

trait SportCar {
  val brand:String 
  def run():String = "Rghhhhh Rghhhhh Rghhhhh...."
}

trait Printable {
  def printIt:Unit 
}

class BMW extends Car with SportCar with Printable{
  override val brand = "BMW"
  override def printIt:Unit = println(brand + " does " + run() )
}

val x1 = new BMW
x1.printIt

