val positiveNumber = new PartialFunction[Int, Int] {
  def apply(n:Int) = n / n
  def isDefinedAt(n:Int) = n != 0
}

println( positiveNumber.isDefinedAt(6) )
println( positiveNumber.isDefinedAt(0) )

println( positiveNumber(6) ) 
println( positiveNumber(0) ) 
