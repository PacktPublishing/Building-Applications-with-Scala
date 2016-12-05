package scalabook.javacode.chap1;

import java.util.Arrays;
import java.util.List;

public class ForLoopsEvenNumberFiltering {
	public static void main(String[] args) {
		List<Integer> listOfValues = Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9,10});
		for(Integer i : listOfValues){
			if (i%2==0) System.out.println(i);	
		}
	}
}
