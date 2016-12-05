trait SportCar {
   def run():String = "Rghhhhh Rghhhhh Rghhhhh...."
}

val bmw = new Object with SportCar
println(bmw)

println( bmw.run )

