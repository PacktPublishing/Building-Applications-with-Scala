class Person(
        @scala.beans.BeanProperty var name:String = "",
        @scala.beans.BeanProperty var age:Int = 0
      ){
        name = name.toUpperCase
        override def toString = "name: " + name + " age: " + age
}

val p  = new Person("Diego",31)
println(p)

val p1 = new Person(age = 31, name = "Diego")
println(p1)

println( p.getAge )

println( p1.getName )

