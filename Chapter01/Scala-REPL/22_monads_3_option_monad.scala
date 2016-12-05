val a:Option[Int] = Some(1)
println(a)

println(a.get)

val b:Option[Int] = None
println(b)

// java.util.NoSuchElementException: None.get
// b.get

println(b.getOrElse(0))

println( a == b )

