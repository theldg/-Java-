import com.ldg.stack.Stack;

public class Main {

	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(11);
		stack.push(22);

		stack.push(33);
		stack.push(44);
		
		System.out.println(stack.peek());
		System.out.println(stack.remove());
		System.out.println(stack.peek());

	}
}
