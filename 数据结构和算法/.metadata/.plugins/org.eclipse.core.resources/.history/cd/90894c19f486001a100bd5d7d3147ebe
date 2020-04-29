package com.ldg.tree;

import java.util.LinkedList;
import java.util.Queue;

import com.ldg.printer.BinaryTreeInfo;

/**
 * 二叉树
 * 
 * @author ldg
 *
 * @param <T>
 */
public class BinaryTree<T> implements BinaryTreeInfo {
	// 元素数量
	protected int size;
	// 根节点
	protected Node<T> root;
	// 树的高度
	protected int height = 0;

	// 声明元素节点类

	protected static class Node<T> {
		T element;
		@SuppressWarnings("unused")
		Node<T> parent;
		Node<T> left;
		Node<T> right;

		// 判断该节点是否为叶子节点
		public boolean isLeaf() {
			return left == null && right == null;
		}

		// 判断该节点度是否为2
		public boolean hasTwoChildren() {
			return left != null && right != null;
		}

		// 对&来说，无论第一个表达式为真还是为假，都会继续执行第二个表达式判断；
		// &&当第一个表达式为false的时候，不再继续执行对后边表达式的判断。
		// 当前节点是否为父节点的左节点
		public boolean isLeftNode() {

			return parent != null && this == parent.left;
		}

		// 当前节点是否为父节点的右节点
		public boolean isRightNode() {

			return parent != null && this == parent.right;
		}

		public Node(T element, Node<T> parent) {
			this.element = element;
			this.parent = parent;
		}

		// 获取兄弟节点
		public Node<T> getSibling() {

			if (isLeftNode()) {
				return parent.right;
			} else if (isRightNode()) {
				return parent.left;
			} else {
				return null;
			}
		}

		@Override
		public String toString() {
			if (parent != null)
				return element + "(" + parent.element + ")";
			return element + "(" + null + ")";
		}

	}

	// 提供子类一个创建节点的方法
	protected Node<T> createNode(T element, Node<T> parent) {

		return new Node<T>(element, parent);
	}

	// 元素数量
	public int size() {
		return size;
	}

	// 清空
	public void clear() {
		if (root == null)
			return;
		root = null;
		size = 0;
	}

	// 判断是否为空
	public boolean isEmpty() {
		return size == 0;
	}

	// 前序遍历
	public void preorderTraversal(Visitor<T> visitor) {
		preorderTraversal(root, visitor);
	}

	// 前序遍历 递归实现
	private void preorderTraversal(Node<T> node, Visitor<T> visitor) {
		// 这个判断是确保递归彻底停下来 必须要加上visitor.flag
		if (node == null | visitor.flag)
			return;
		// 自定义怎么实现元素 并且可以随时停止遍历
		visitor.flag = visitor.vistor(node.element);
		preorderTraversal(node.left, visitor);
		preorderTraversal(node.right, visitor);

	}

	// 中序遍历
	public void inorderTraversal(Visitor<T> visitor) {
		inorderTraversal(root, visitor);
	}

	// 中序遍历 递归实现
	private void inorderTraversal(Node<T> node, Visitor<T> visitor) {
		// 这个判断是确保递归彻底停下来 必须要加上visitor.flag
		if (node == null | visitor.flag)
			return;

		inorderTraversal(node.left, visitor);
		// 这个判断是确保不会打印后面的元素
		if (visitor.flag)
			return;
		visitor.flag = visitor.vistor(node.element);
		inorderTraversal(node.right, visitor);
	}

	// 后序遍历
	public void postorderTraversal(Visitor<T> visitor) {

		postorderTraversal(root, visitor);
	}

	// 后序遍历 递归实现
	private void postorderTraversal(Node<T> node, Visitor<T> visitor) {

		// 这个判断是确保递归彻底停下来 必须要加上visitor.flag
		if (node == null | visitor.flag)
			return;

		postorderTraversal(node.left, visitor);
		postorderTraversal(node.right, visitor);
		// 这个判断是确保不会打印后面的元素
		if (visitor.flag)
			return;
		visitor.flag = visitor.vistor(node.element);

	}

	// 层序遍历 队列实现 注意一下官方的Linkedlist允许添加null元素
	public void levelorderTraversal(Visitor<T> visitor) {
		if (root == null | visitor == null)
			return;
		Queue<Node<T>> queue = new LinkedList<>();
		Node<T> node = root;
		// 首节点入队
		queue.offer(node);
		// 队首节点出队的同时 该节点的左右子节点依次入队
		while (!queue.isEmpty()) {
			node = queue.peek();
			if (visitor.vistor(queue.poll().element))
				return;

			// 如果有左子节点 让它入队
			if (node.left != null)
				queue.offer(node.left);
			// 如果有右子节点 让它入队
			if (node.right != null)
				queue.offer(node.right);

		}

	}

	/**
	 * 获取二叉树的高度 递归实现
	 * 
	 * @return 高度
	 */
	public int height() {
		height = height(root);
		return height;
	}

	/**
	 * 
	 * @param node
	 * @return 返回当前节点的高度
	 */
	private int height(Node<T> node) {
		if (node == null)
			return 0;
		// 某个节点的高度=其左右节点最大高度+1
		return Math.max(height(node.left), height(node.right)) + 1;
	}

	/**
	 * 迭代实现 层序遍历实现 当一层元素遍历完 高度+1
	 * 
	 * @return 返回树的高度
	 */
	public int heightTwo() {
		height = 0;
		if (root == null)
			return height;
		Queue<Node<T>> queue = new LinkedList<>();

		Node<T> node = root;
		// 首节点入队
		queue.offer(node);

		// 一层的元素数量
		int levelsize = 1;
		// 队首节点出队的同时 该节点的左右子节点依次入队
		while (!queue.isEmpty()) {
			// 队首元素出队
			node = queue.poll();
			levelsize--;
			// 如果有左子节点 让它入队
			if (node.left != null)
				queue.offer(node.left);
			// 如果有右子节点 让它入队
			if (node.right != null)
				queue.offer(node.right);
			// 判断这一层是否遍历完
			if (levelsize == 0) {
				// 记录下一层的元素个数
				levelsize = queue.size();
				height++;

			}

		}
		return height;
	}

	/**
	 * 判断是否为完全二叉树 层序遍历实现
	 * 
	 * @return
	 */
	public boolean iscomplete() {
		if (root == null)
			return false;
		Queue<Node<T>> queue = new LinkedList<Node<T>>();
		queue.offer(root);
		// 节点不是叶子节点
		boolean leaf = false;
		while (!queue.isEmpty()) {
			Node<T> node = queue.poll();
			// 当节点不是叶子节点 而leaf=true
			if (leaf && !node.isLeaf())
				return false;

			if (node.left != null)
				queue.offer(node.left);
			else {
				// node.left=null&&node.right!=null情况
				if (node.right != null)
					return false;
			}
			// node.left!=null&&node.right！=null情况
			if (node.right != null)
				queue.offer(node.right);
			else {
				// 包含 node.left=null&&node.right==null 情况
				// 包含 node.left!=null&&node.right==null 情况
				// 后面的所有节点都要是叶子节点
				leaf = true;
			}
		}
		return true;
	}

	// 提供外界自定义遍历结果如何显示接口
	public static abstract class Visitor<T> {
		// 是否停止遍历的标志
		private boolean flag = false;

		/**
		 * 怎么显示element数据及可以随时停止遍历(根据元素)
		 * 
		 * @param element 数据
		 */
		public abstract boolean vistor(T element);
	}

	// 获取某个节点的前驱节点
	protected Node<T> getpredecessor(Node<T> node) {
		if (node == null)
			return null;
		if (node.left != null) {
			Node<T> tempNode = node.left;
			while (tempNode.right != null) {
				tempNode = tempNode.right;
			}

			return tempNode;
		} else {
			if (node.parent == null) {
				return null;
			} else {
				{
					Node<T> tempNode = node.parent;
					while (tempNode.parent != null) {
						if (tempNode == tempNode.parent.left) {
							return tempNode.parent;
						}
						tempNode = tempNode.parent;

					}

					return tempNode.parent;
				}
			}
		}

	}

	// 获取某个节点的后继节点
	protected Node<T> getSuccessor(Node<T> node) {
		if (node == null)
			return null;
		if (node.right != null) {
			Node<T> tempNode = node.right;
			while (tempNode.left != null) {
				tempNode = tempNode.left;
			}

			return tempNode;
		} else {
			if (node.parent == null) {
				return null;
			} else {
				{
					Node<T> tempNode = node.parent;
					while (tempNode.parent != null) {
						if (tempNode == tempNode.parent.right) {
							return tempNode.parent;
						}
						tempNode = tempNode.parent;

					}

					return tempNode.parent;
				}
			}
		}

	}

	// ************************* BinaryTreeInfo
	@Override
	public Object root() {

		return root;
	}

	@Override
	public Object left(Object node) {
		// TODO Auto-generated method stub
		return ((Node) node).left;
	}

	@Override
	public Object right(Object node) {
		// TODO Auto-generated method stub
		return ((Node) node).right;
	}

	// 怎么处理这个节点
	@Override
	public Object string(Object node) {
		// TODO Auto-generated method stub
		return node;
	}

}
