class Person(
        @scala.beans.BeanProperty var name:String = "",
        @scala.beans.BeanProperty var age:Int = 0
   ){
       name = name.toUpperCase
       override def toString = "name: " + name + " age: " + age
}

class LowerCasePerson(name:String,age:Int) extends Person(name,age) {
   setName(name.toLowerCase)
}

val p  = new LowerCasePerson("DIEGO PACHECO",31)
println(p)

println( p.getName )
