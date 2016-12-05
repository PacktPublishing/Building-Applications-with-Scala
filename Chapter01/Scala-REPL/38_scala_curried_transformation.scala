def normalSum(x:Int,y:Int):Int=x+y

val curriedSum = (normalSum _).curried

val add3= curriedSum(3)

println(add3(3))
