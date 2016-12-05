// Creates the numbers 1,2,3,4,5 and them multiply they by 2 and creates a new Vector
println ((1 to 5).map(_*2))   

// Creates 1,2,3 and sum them all with each orher and return the total
println ( (1 to 3).reduceLeft(_+_) )

// Creates 1,2,3 and multiply each number by it self and return a Vector
println ( (1 to 3).map( x=> x*x ) )

// Creates numbers 1,2,3,4 ans 5 filter only Odd numbers them multiply them odds by 2 and return a Vector
println ( (1 to 5) filter { _%2 == 0 } map { _*2 } )

// Creates a List with 1 to 5 and them print each element being multiplyed by 2
List(1,2,3,4,5).foreach ( (i:Int) => println(i * 2 ) )	

// Creates a List with 1 to 5 and them print each element being multiplyed by 2
List(1,2,3,4,5).foreach ( i => println(i * 2) )	

// Drops 3 elements from the lists
println( List(2,3,4,5,6).drop(3))
println( List(2,3,4,5,6) drop 3 )

// Zip 2 lists into a single one: It will take 1 element of each list and create a pair List
println(  List(1,2,3,4).zip( List(6,7,8) )) 

// Take nested lists and create a single list with flat elements
println( List(List(1, 2), List(3, 4)).flatten )

// Finds a person in a List by Age
case class Person(age:Int,name:String)
println( List(Person(31,"Diego"),Person(40,"Nilseu")).find( (p:Person) => p.age <= 33 ) )



