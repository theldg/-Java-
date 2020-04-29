package 队列;

/**
 * 使用一个队列
 * https://leetcode-cn.com/problems/implement-stack-using-queues/
 * @author Administrator
 *
 */
import java.util.LinkedList;
import java.util.Queue;

public class _225_用队列实现栈02 {

	/** Initialize your data structure here. */
	public _225_用队列实现栈02() {

	}

	private Queue<Integer> inQueue = new LinkedList<Integer>();

	/** Push element x onto stack. */
	public void push(int x) {
		inQueue.offer(x);
        //冒泡将队列前面的元素添加到队尾 队尾变成队首
		for (int i = 1; i < inQueue.size(); i++) {
			inQueue.offer(inQueue.remove());
		}
	}

	/** Removes the element on top of the stack and returns that element. */
	public int pop() {

		return inQueue.remove();
	}

	/** Get the top element. */
	public int top() {
		return inQueue.peek();
	}

	/** Returns whether the stack is empty. */
	public boolean empty() {

		return inQueue.isEmpty();
	}

}
