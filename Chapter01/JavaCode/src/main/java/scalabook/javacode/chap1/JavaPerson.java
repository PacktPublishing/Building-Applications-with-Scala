package scalabook.javacode.chap1;

public class JavaPerson {
	
	private String name;
	private Integer age;
	
	public JavaPerson() {}

	public JavaPerson(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}

	public JavaPerson(String name) {
		super();
		this.name = name;
	}

	public JavaPerson(Integer age) {
		super();
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
