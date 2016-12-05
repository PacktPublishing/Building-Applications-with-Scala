case class Person(name: String, age: Int)

val p = Person("Diego",31)
println(p)

val p2 = Person("Diego",32)
println(p2)

println( p.name )

println( p.age )

println( p == p  )

println( p.toString )

println( p.hashCode )

println( p.equals(p2) )

println( p.equals(p) )
