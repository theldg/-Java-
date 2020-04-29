package test;

public class Test<T> {

	protected static class  s<T>{
		String  name;
		s<T>  st;
		public   s(String  name,s<T> st)
		{
			this.name=name;
			this.st=st;
		}
		
	}
}
