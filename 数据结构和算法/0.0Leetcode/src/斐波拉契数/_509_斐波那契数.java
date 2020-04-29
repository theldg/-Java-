package 斐波拉契数;
/**
 * https://leetcode-cn.com/problems/fibonacci-number/
 * @author Administrator
 *
 */
public class _509_斐波那契数 {
    /**
     * 递归思想
     * @param n 第几个数
     */
	public  int   fib(int n)
	{
		if(n<=2) {
			return  n-1;
		}
		
		return  fib(n-1)+fib(n-2);
	}
	
	  /**
     *迭代思想
     * @param n 第几个数
     */
	public  int  fib2(int n)
	{
		if(n<=2) {
			return  n-1;
		}
		int one=0;
		int two=1;
		
		for(int i=0;i<n-2;i++)
		{
			int sum=one+two;
			one=two;
			two=sum;
		}
		return two;
	}
}
