package 递归测试;

public class Test {
	
	
	
	public static  int   sum(int n)
	{
		if(n==1)
		{
			return 1;
		}
	
	    return   sum(n-1)+n;
	}
	
	public static void main(String[] args) {
		 System.out.println( sum(4));
	}

}
