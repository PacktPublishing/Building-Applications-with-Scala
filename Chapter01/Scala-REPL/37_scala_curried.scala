// Function Definition
def sum(x:Int)(y:Int):Int = x+y

// Function call - Calling a curried function 
sum(2)(3)

// Doing partial with Curried functions
val add3 = sum(3) _

// Supply the last argument now
add3(3)