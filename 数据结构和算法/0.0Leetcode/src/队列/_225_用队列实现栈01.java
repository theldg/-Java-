package 队列;

/**
  * 使用两个队列进行操作
 * https://leetcode-cn.com/problems/implement-stack-using-queues/
 * @author Administrator
 *
 */
import java.util.LinkedList;
import java.util.Queue;

public class _225_用队列实现栈01 {

	/** Initialize your data structure here. */
	public _225_用队列实现栈01() {

	}

	private Queue<Integer> inQueue = new LinkedList<Integer>();
	private Queue<Integer> outQueue = new LinkedList<Integer>();
	//栈顶元素
    private  int  top;
	/** Push element x onto stack. */
	public void push(int x) {
		inQueue.offer(x);
		top=x;
	}

	/** Removes the element on top of the stack and returns that element. */
	public int pop() {
		
		int  old=0;
		while (inQueue.size()>1) {
			old=inQueue.remove();
			top=old;
			outQueue.offer(top);
        }     
		old=inQueue.remove();
		Queue<Integer> temp=inQueue;
		inQueue=outQueue;
		outQueue=temp;
		return  old;
	}

	/** Get the top element. */
	public int top() {
		return top;
	}

	/** Returns whether the stack is empty. */
	public boolean empty() {

		return inQueue.isEmpty();
	}

}
