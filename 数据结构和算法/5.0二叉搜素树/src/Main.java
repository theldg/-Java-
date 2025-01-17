import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.ldg.binarysearchtree.BinarySearchTree;
import com.ldg.binarysearchtree.BinarySearchTree.Visitor;
import com.ldg.model.Car;
import com.ldg.model.People;
import com.ldg.printer.BinaryTrees;

public class Main {

	public static void main(String[] args) {
		其他测试一();
	}

	private static void 其他测试一() {
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();

		bst.add(8);
		bst.add(4);
		bst.add(7);
		bst.add(10);
		bst.add(46);
		bst.add(51);
		bst.add(78);
		bst.add(1);
		bst.add(2);
		bst.add(9);

		BinaryTrees.println(bst);

		System.out.println("树的高度:" + bst.height());
		System.out.println("是否是完全二叉树:" + bst.iscomplete());
		System.out.println("层序遍历:");
		bst.levelorderTraversal(new Visitor<Integer>() {
			
			@Override
			public boolean vistor(Integer element) {
				System.out.print(element+"_");
				return false;
			}
		});

	}

	private static void 遍历测试一() {
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		bst.add(15);
		bst.add(5);
		bst.add(4);
		bst.add(6);
		bst.add(3);
		bst.add(19);
		bst.add(78);
		bst.add(1);
		System.out.println("前序遍历:");
		// 前序遍历
		bst.preorderTraversal(new Visitor<Integer>() {

			@Override
			public boolean vistor(Integer element) {
				System.out.println("[" + element + "]");
				return element == 15 ? true : false;
			}
		});
		System.out.println("中序遍历:");
		// 中序遍历
		bst.inorderTraversal(new Visitor<Integer>() {

			@Override
			public boolean vistor(Integer element) {
				System.out.println("[" + element + "]");
				return element == 6 ? true : false;
			}
		});
		System.out.println("后序遍历:");
		// 后序遍历
		bst.postorderTraversal(new Visitor<Integer>() {

			@Override
			public boolean vistor(Integer element) {
				System.out.println("[" + element + "]");
				return element == 3 ? true : false;
			}
		});
		System.out.println("层序遍历:");
		// 层序遍历
		bst.levelorderTraversal(new Visitor<Integer>() {

			@Override
			public boolean vistor(Integer element) {

				System.out.println("[" + element + "]");
				return element == 4 ? true : false;
			}
		});
		System.out.println("测试LinkedList:");
		// 官方的LinkedList允许添加null元素
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.offer(null);
		queue.offer(null);
		queue.offer(null);
		System.out.println(queue);
		System.out.println(queue.isEmpty());
	}

	private static void test01() {
		BinarySearchTree<People> bst = new BinarySearchTree<People>(new Comparator<People>() {
			// 小的元素放在右边 大的元素放在左边
			@Override
			public int compare(People o1, People o2) {

				return o2.getAge() - o1.getAge();
			}
		});

		System.out.println(bst.add(new People("ldg", 5)));
		System.out.println(bst.add(new People("ldg", 2)));
		System.out.println(bst.add(new People("ldg", 3)));
		System.out.println(bst.add(new People("ldg", 8)));
		System.out.println(bst.add(new People("ldg", 7)));
		System.out.println(bst.add(new People("ldg", 1)));

//		BinarySearchTree<Car> bst2 = new BinarySearchTree<Car>();
//		System.out.println(bst2.add(new Car(1)));
//		System.out.println(bst2.add(new Car(2)));
	}

}
