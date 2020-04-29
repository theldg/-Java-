package test;

public class Main {

	public static void main(String[] args) {
		Test  test=new Test("a",new Test("ldg"));
		System.out.println(test);
		Test test2=new  Test1("zjb",test);
		System.out.println(test2);

	}
}
