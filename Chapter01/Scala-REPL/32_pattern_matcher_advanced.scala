trait Color
case class Red(saturation: Int)   extends Color
case class Green(saturation: Int) extends Color
case class Blue(saturation: Int)  extends Color

def matcher(arg:Any): String = arg match {
  	case "Scala"               => "A Awesome Language"
  	case x: Int                => "An Int with value " + x
  	case Red(100)              => "Red sat 100"
  	case Red(_)                => "Any kind of RED sat"
  	case Green(s) if s == 233  => "Green sat 233"
  	case Green(s)              => "Green sat " + s
  	case c: Color              => "Some Color: " + c
  	case w: Any                => "Whatever: " + w
}

println(matcher("Scala"))
println(matcher(1))
println(matcher(Red(100)))
println(matcher(Red(160)))
println(matcher(Green(160)))
println(matcher(Green(233)))
println(matcher(Blue(111)))
println(matcher(false))
println(matcher(new Object))

