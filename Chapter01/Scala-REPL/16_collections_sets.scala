var names = scala.collection.mutable.SortedSet[String]("Diego", "Poletto", "Jackson")
println(names)

names += "Sam"
println(names)

println(names("Diego"))

names -= "Jackson"
println(names)
