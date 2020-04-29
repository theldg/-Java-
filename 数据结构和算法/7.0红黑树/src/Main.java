import java.util.ArrayList;
import java.util.List;

import com.ldg.printer.BinaryTrees;
import com.ldg.tree.AVLTree;
import com.ldg.tree.BinarySearchTree;
import com.ldg.tree.RBTree;

public class Main {

	public static void main(String[] args) {
		其他测试四();
	}

	private static void 其他测试一() {
		AVLTree<Integer> bst = new AVLTree<Integer>();

		
		
		int[] array = new int[] {20, 1, 53, 46, 54, 35, 28, 18, 5, 42, 47, 31, 43, 9, 38, 27};
		for (int i = 0; i < array.length; i++) {
			bst.add(array[i]);
			
		}
		System.out.println(bst.size());
		BinaryTrees.println(bst);
		bst.remove(35);
		System.out.println(bst.size());
		BinaryTrees.println(bst);
		bst.clear();
		System.out.println(bst.size());
		BinaryTrees.println(bst);
	}

	private static void 其他测试四() {
		RBTree<Integer> rb = new RBTree<Integer>();
		int[] array = new int[] {4,3,6,8,5,2};
		for (int i = 0; i < array.length; i++) {
			rb.add(array[i]);
			
		}
		System.out.println("RBTree");
		BinaryTrees.println(rb);
	
		System.out.println();
		System.out.println("红黑树高度:"+rb.height());
		System.out.println("添加10后");
		rb.add(10);
		BinaryTrees.print(rb);
		System.out.println();
		System.out.println("删除5后");
		rb.remove(5);
		BinaryTrees.println(rb);
		System.out.println("删除4后");
		rb.remove(4);
		BinaryTrees.println(rb);
		

	}

	private static void 其他测试二() {
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
		System.out.println(bst.height());
		System.out.println(bst.heightTwo());

	}

	private static void 其他测试三() {
		AVLTree<Integer> bst = new AVLTree<Integer>();

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
		System.out.println();
		bst.remove(9);
		bst.remove(8);
		BinaryTrees.println(bst);
		bst.remove(4);
		bst.add(100);
		BinaryTrees.println(bst);
		bst.add(101);
		BinaryTrees.println(bst);
		System.out.println();
		bst.remove(78);
		bst.remove(46);
		System.out.println(bst.contains(78));
		BinaryTrees.println(bst);

	}

}
