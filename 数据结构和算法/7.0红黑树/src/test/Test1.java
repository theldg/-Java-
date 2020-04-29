package test;

public class Test1 extends Test {
	public Test1(String name,Test test) {
		this.name = name;
		this.test=test;
	}

	@Override
	public String toString() {
		return name + " : " + test;
	}

}
