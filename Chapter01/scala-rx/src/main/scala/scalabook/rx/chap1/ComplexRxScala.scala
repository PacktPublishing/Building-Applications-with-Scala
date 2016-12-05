package scalabook.rx.chap1

import rx.lang.scala.Observable

object ComplexRxScala extends App {
  
  Observable.
      just(1,2,3,4,5,6,7,8,9,10).       // 1,2,3,4,5,6,7,8,9,10
      filter( x => x%2==0).             // 2,4,6,8,10
      take(2).                          // 2,4
      reduce(_+_).                      // 6
      subscribe( r => println(s"#1 $r"))
  
   val o1 = Observable.
            just(1,2,3,4,5,6,7,8,9,10).  // 1,2,3,4,5,6,7,8,9,10
            filter( x => x%2==0).        // 2, 4, 6, 8, 10
            take(3).                     // 2, 4 ,6    
            map( n => n * n)             // 4, 16, 36
  
   val o2 = Observable.                 
            just(1,2,3,4,5,6,7,8,9,10). // 1,2,3,4,5,6,7,8,9,10 
            filter( x => x%2!=0).       // 1, 3, 5, 7, 9   
            take(3).                    // 1, 3, 5
            map( n => n * n)            // 1, 9, 25
           
   val o3 = o1.
           merge(o2).                  // 2,16, 36, 1, 9, 25
           subscribe( r => println(s"#2 $r"))
      
}