implicit val yValue:Int = 6

def sum(x:Int)(implicit yValue:Int) = x + yValue

val result = sum(10)
println(result)