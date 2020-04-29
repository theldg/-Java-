package test;

public class Test {

	String name;
	Test test;

	public Test(String name, Test test) {

		this.name = name;
		this.test = test;

	}

	public Test() {

	}

	public Test(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name+" : "+test;
	}
}
