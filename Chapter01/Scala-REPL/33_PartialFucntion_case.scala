val positiveNumber:PartialFunction[Int, Int]  =  {
  case n: Int if n != 0 => n / n
}

println( positiveNumber.isDefinedAt(6) )
println( positiveNumber.isDefinedAt(0) )

println( positiveNumber(6) ) 
println( positiveNumber(0) ) 
