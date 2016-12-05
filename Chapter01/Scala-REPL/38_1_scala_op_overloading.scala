case class MyNumber(value:Int){
	def +(that:MyNumber):MyNumber = new MyNumber(that.value + this.value)
	def +(that:Int):MyNumber = new MyNumber(that + this.value)
}

val v = new MyNumber(5)

println(v)

println(v + v)

println(v + new MyNumber(4))

println(v + 8)