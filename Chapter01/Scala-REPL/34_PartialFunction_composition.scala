val even:PartialFunction[Int, String] = {
  case i if i%2 == 0 => "even"
}

val odd:PartialFunction[Int, String] = { case _ => "odd"}

val evenOrOdd:(Int => String) = even orElse odd

println( evenOrOdd(1) == "odd"  )
println( evenOrOdd(2) == "even" )


