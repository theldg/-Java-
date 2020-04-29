package 栈;

import java.util.Stack;

//声明两个stack
public class _232_用栈实现队列 {

	// 一个用来push
	Stack<Integer> inStack = new Stack<Integer>();
	// 一个用来peek pop
	Stack<Integer> outStack = new Stack<Integer>();

	// 入队
	public void push(int x) {

		inStack.push(x);

	}

	// 出队
	public int pop() {
		// 判断outStack是不是为空
		if (outStack.isEmpty()) {
			// 将inStack的元素放入outStack中
			while (!inStack.isEmpty()) {
				outStack.push(inStack.pop());
			}
		}

		return outStack.pop();

	}

	// 查看首部元素
	public int peek() {

		if (outStack.isEmpty()) {
			// 将inStack的元素放入outStack中
			while (!inStack.isEmpty()) {
				outStack.push(inStack.pop());
			}
		}

		return outStack.peek();
	}

	//判断队列是否为空
	public boolean empty() {

		return inStack.isEmpty() && outStack.isEmpty();

	}
   
	
}
