def factorial(n:Int):Int = n match {
    case 0 => 1
    case n => n * factorial(n - 1)
}

println(factorial(3))
println(factorial(6))

