package 二叉树;

import java.util.LinkedList;
import java.util.Queue;

import javax.xml.soap.Node;

public class _226_翻转二叉树 {

	// 遍历到每个节点 将该节点的左右节点反转

	public TreeNode invertTree(TreeNode root) {

		preOrderTraversal(root);
		return root;
	}

	// 前序遍历
	private void preOrderTraversal(TreeNode node) {
		if (node == null)
			return;
		TreeNode tempNode = node.left;
		node.left = node.right;
		node.right = tempNode;
		preOrderTraversal(node.right);
		preOrderTraversal(node.left);

	}

	// 中序遍历
	private void inOrderTraversal(TreeNode node) {
		if (node == null)
			return;
		preOrderTraversal(node.left);
		TreeNode tempNode = node.left;
		node.left = node.right;
		node.right = tempNode;
		preOrderTraversal(node.left);

	}

	// 后序遍历
	private void postOrderTraversal(TreeNode node) {
		if (node == null)
			return;
		postOrderTraversal(node.left);
		postOrderTraversal(node.right);
		TreeNode tempNode = node.left;
		node.left = node.right;
		node.right = tempNode;
	}

	// 层序遍历

	private void levelOrderTracersal(TreeNode node) {
		if (node == null)
			return;

		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(node);
		TreeNode node2;

		while (!queue.isEmpty()) {
			node2 = queue.poll();
			TreeNode tempNode = node2.left;
			node2.left = node2.right;
			node2.right = tempNode;
			if (node2.left != null) {
				queue.add(node2.left);
			}
			if (node2.right != null) {
				queue.add(node2.right);
			}
			
		}

	}

}
