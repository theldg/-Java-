import java.util.ArrayList;
import java.util.List;

import com.ldg.printer.BinaryTrees;
import com.ldg.tree.AVLTree;
import com.ldg.tree.BinarySearchTree;

public class Main {

	public static void main(String[] args) {
		其他测试一();
	}

	private static void 其他测试一() {
		AVLTree<Integer> avl = new AVLTree<Integer>();

		List<Integer> bst = new ArrayList<Integer>();
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
		bst.add(80);

		for (Integer i : bst) {
			avl.add(i);

		}
		System.out.println("AVL树");
		BinaryTrees.print(avl);
		System.out.println();
		System.out.println("树的高度:"+avl.height());
		System.out.println("是否为完全二叉树:"+avl.iscomplete());
		System.out.println("添加元素90后:");
		avl.add(90);
		BinaryTrees.println(avl);

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
