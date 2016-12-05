val c = Some("one")
println(c)

println( c.flatMap( s => Some(s.toUpperCase) ) )
println(c)

