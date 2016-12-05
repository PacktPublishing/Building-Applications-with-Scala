package scalabook.javacode.chap1;

import java.util.LinkedHashSet;
import java.util.Set;

public class JavaNoForComprehension {
	public static void main(String[] args) {
		
		Set<String> names = new LinkedHashSet<>();
		Set<String> brazillians = new LinkedHashSet<>();
		
		names.add("Diego");
		names.add("James");
		names.add("John");
		names.add("Sam");
		names.add("Christophe");
		
		for (String name: names){
			if (name.contains("Die")) brazillians.add(name); 
		}
		
		System.out.println(brazillians);
	}
}
