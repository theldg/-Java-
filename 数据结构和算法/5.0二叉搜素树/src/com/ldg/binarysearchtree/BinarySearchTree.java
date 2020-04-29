package com.ldg.binarysearchtree;

import java.util.Comparator;

import java.util.LinkedList;
import java.util.Queue;

import com.ldg.printer.BinaryTreeInfo;

//二叉搜索树 默认所添加的元素必须具有可比较性 (一:要么声明对象时传入相应比较器  二:要么实现Comparable接口)
//实现BinaryTreeInfo可以打印树状
public class BinarySearchTree<T> implements BinaryTreeInfo {
	// 元素数量
	private int size;
	// 根节点
	private Node<T> root;
	// 树的高度
	private int height = 0;

	// 比较器:元素是怎么比较的
	private Comparator<T> comparator;

	// 声明元素节点类
	@SuppressWarnings("hiding")
	private class Node<T> {
		T element;
		@SuppressWarnings("unused")
		Node<T> parent;
		Node<T> left;
		Node<T> right;

		// 判断该节点是否为叶子节点
		public boolean isLeaf() {
			return left == null && right == null;
		}

		public boolean hasTwoChildren() {
			return left != null && right != null;
		}

		public Node(T element, Node<T> parent) {
			this.element = element;
			this.parent = parent;
		}

		@Override
		public String toString() {
			return "" + element;
		}
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
	}

	// 判断是否为空
	public boolean isEmpty() {
		return size == 0;
	}

	// 添加元素
	public T add(T element) {

		// 判断是否是根节点
		if (root == null) {
			root = new Node<T>(element, null);
			return null;
		} else {
			Node<T> node = root;
			Node<T> parent = null;

			int result = 0;
			// 一直遍历 直到最后一层
			while (node != null) {
				parent = node;
				//比较结果
				result = compare(element, node.element);
				if (result > 0) {

					node = node.right;
				} else if (result < 0) {
					node = node.left;
				} else {
					return node.element;
				}
			}
			// 根据结果判断是添加到右子树还是左子树
			if (result > 0) {

				parent.right = new Node<T>(element, parent);
				size++;
				return parent.element;
			} else {
				parent.left = new Node<T>(element, parent);
				size++;
				return parent.element;
			}
		}
	}

	// 移除元素
	public void remove(T element) {
		Node<T> node = getNode(element);
		remove(node);

	}

	// 根据元素找到节点
	private Node<T> getNode(T element) {
		Node<T> node = root;
		while (node != null) {
			int result = compare(node.element, element);
			if (result == 0) {
				return node;
			} else if (result < 0) {
				node = node.right;

			} else {
				node = node.left;
			}
		}
		return null;
	}

	// 移除节点
	private void remove(Node<T> node) {
		// 删除节点度为2的节点
		if (node.hasTwoChildren()) {
			// 直接将它的前驱节点的值赋给该节点 然后删除前驱节点
			node.element = getpredecessor(node).element;
			// 前驱节点 后继节点的度只能为1 或者0
			// 所以直接赋值给node 通过后续删除度为0或1节点来间接删除
			node = getpredecessor(node);
		}
		// 删除度为0或1的节点
		if (node.isLeaf()) {
			if (node.parent == null) {
				root = null;
			} else {
				if (node == node.parent.left) {
					node.parent.left = null;
					node.parent = null;

				} else {
					node.parent.right = null;
					node.parent = null;

				}
			}
		} else {

			if (node.parent == null) {
				if (node.left != null) {
					root = node.left;
					root.parent = null;
				} else {
					root = node.right;
					root.parent = null;
				}

			} else {

				if (node == node.parent.left) {
					if (node.left != null) {
						node.left.parent = node.parent;
						node.parent.left = node.left;
					} else {
						node.right.parent = node.parent;
						node.parent.left = node.right;
					}
				} else {
					if (node.left != null) {
						node.left.parent = node.parent;
						node.parent.right = node.left;
					} else {
						node.right.parent = node.parent;
						node.parent.right = node.right;
					}
				}

			}

		}

	}

	// 是否包含元素
	public boolean contains(T element) {

		if (root == null)
			return false;
		Node<T> node = root;
		while (node != null) {
			int reslut = compare(node.element, element);
			if(reslut==0)
			{
			   return  true;
			}else if(reslut>0)
			{
				node=node.left;
			}else {
				node=node.right;
			}
		}
		return false;
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
	 * 高度  ：某条路径的最多节点个数
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

	// 重写tostring方法 将树树状结构显示出来
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		toString(root, sb, "");
		return sb.toString();

	}

	// 递归遍历每个元素 并加上前缀
	private void toString(Node<T> node, StringBuilder sb, String prefix) {
		if (node == null)
			return;
		toString(node.left, sb, prefix + "[L]");
		sb.append(prefix).append(node.element).append("\n");
		toString(node.right, sb, prefix + "[R]");
	}

	public BinarySearchTree() {

	}

	public BinarySearchTree(Comparator<T> comparator) {

		this.comparator = comparator;
	}

	// 比较方法
	@SuppressWarnings("unchecked")
	private int compare(T element, T element2) {
		// 判断是否有传入比较器
		if (comparator != null)
			// 具体比较要看传入的比较器
			return comparator.compare(element, element2);
		else
			// 没有传入比较器就用元素自己的默认比较方法比较 如果元素不具有比较性就会报错
			return ((Comparable<T>) element).compareTo(element2);

	}


	// 获取某个节点的前驱节点
	private Node<T> getpredecessor(Node<T> node) {
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
	private Node<T> getSuccessor(Node<T> node) {
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

//************************* BinaryTreeInfo
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

	@Override
	public Object string(Object node) {
		// TODO Auto-generated method stub
		return ((Node) node) + "(" + ((Node) node).parent + ")";
	}

}
