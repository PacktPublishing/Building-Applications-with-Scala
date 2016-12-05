def resolve(choice:Int):String = choice match {
    case 1 => "yes"
    case 0 => "no"
    case _ => throw new IllegalArgumentException("Valid arguments are: 0 or 1. Your arg is: " + choice)
}

println(resolve(0))
println(resolve(1))

try {
  println(resolve(33))
} catch{
  case e:Exception => println("Something Went Worng. EX: " + e)
}
