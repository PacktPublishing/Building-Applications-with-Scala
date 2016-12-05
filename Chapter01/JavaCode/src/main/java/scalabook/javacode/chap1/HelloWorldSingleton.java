package scalabook.javacode.chap1;

public class HelloWorldSingleton {
	
	private HelloWorldSingleton(){}
	
	private static class SingletonHelper{
	    private static final HelloWorldSingleton INSTANCE = 
            new HelloWorldSingleton();
	}
	
	public static HelloWorldSingleton getInstance(){
	    return SingletonHelper.INSTANCE;
	}
	
	public void sayHello(){
		System.out.println("Hello World");
	}
	
	public static void main(String[] args) {
		getInstance().sayHello();
	}
}
