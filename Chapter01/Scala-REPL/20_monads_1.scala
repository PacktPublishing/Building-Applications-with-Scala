val numbers = List(1,2,3,4,5,6)
println(numbers)

def doubleIt(i:Int):Double = i * 2

var doubled = numbers.map( doubleIt _ )
println(doubled)

doubled = numbers.map( 2.0 * _ )
println(doubled)
