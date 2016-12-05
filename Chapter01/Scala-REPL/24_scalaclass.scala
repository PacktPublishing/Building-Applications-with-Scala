class Calculator {
  def add(a: Int, b: Int): Int = a + b
  def multiply(n: Int, f: Int): Int = n * f
}


val c = new Calculator
println(c)

println( c.add(1,2) )

println( c.multiply(3,2) )
