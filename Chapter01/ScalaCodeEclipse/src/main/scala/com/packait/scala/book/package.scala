package com.packait.scala.book

package object commons {
  
  val PI = 3.1415926
  
  object constraintsHolder {
    val ODD = "Odd"
    val EVEN = "Even"
  }
  
  def isOdd(n:Int):String  = if (n%2==0) constraintsHolder.ODD else null  

  def isEven(n:Int):String = if (n%2!=0) constraintsHolder.EVEN else null
  
  def show(s:String) = println(s)
  
}