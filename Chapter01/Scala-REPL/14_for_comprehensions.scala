val names = Set("Diego", "James", "John", "Sam", "Christophe")
println(names)

val brazillians = for {
        name <- names  
        initial <- name.substring(0, 1)
} yield if (name.contains("Die")) name
println(brazillians)
