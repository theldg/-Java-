package com.ldg.tree;

import java.util.Comparator;

/**
 * 二叉搜索树
 * 
 * @author ldg
 * @param <T>
 */
//二叉搜索树   默认所添加的元素必须具有可比较性 (一:要么声明对象时传入相应比较器  二:要么实现Comparable接口)
public class BinarySearchTree<T> extends BinaryTree<T> {

	// 比较器:元素是怎么比较的
	private Comparator<T> comparator;

	// 创造属于自己的节点
	protected Node<T> createNode(T element, Node<T> parent) {

		return new Node<T>(element, parent);
	}

	// 添加元素 默认大的放在右边 小的反在左边
	// 也可以自己传入比较器
	public T add(T element) {

		// 判断是否有根节点
		if (root == null) {
			root = createNode(element, null);
			size++;
			afterAdd(root);
			return null;
		} else {
			Node<T> node = root;
			Node<T> parent = null;

			int result = 0;
			// 一直遍历 直到最后一层
			while (node != null) {
				parent = node;
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
			Node<T> newNode = createNode(element, parent);
			if (result > 0) {

				parent.right = newNode;
				size++;

			} else {
				parent.left = newNode;
				size++;

			}
			afterAdd(newNode);
			return parent.element;
		}
	}

	// 添加元素后要做的操作
	protected void afterAdd(Node<T> node) {

	}

	// 移除元素
	public void remove(T element) {
		Node<T> node = getNode(element);
		remove(node);

	}

	/**
	 * 移除元素后要进行的操作
	 * 
	 * @param node 删除的节点或者是用于替代的节点
	 *
	 */
	protected void afterRemove(Node<T> node) {

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
		// 用于取代的节点
		Node<T> replace;
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
				return;
			} else {
				if (node.isLeftNode()) {
					node.parent.left = null;

				} else {
					node.parent.right = null;

				}
				size--;
				// node是被删除的节点
				afterRemove(node);
				return;
			}
		} else {

			if (node.parent == null) {
				if (node.left != null) {
					replace = node.left;
					root = replace;

				} else {
					replace = node.right;
					root = replace;

				}

			} else {

				if (node.isLeftNode()) {
					if (node.left != null) {
						replace = node.left;
						replace.parent = node.parent;
						node.parent.left = replace;
					} else {
						replace = node.right;
						replace.parent = node.parent;
						node.parent.left = replace;
					}

				} else {
					if (node.left != null) {
						replace = node.left;
						replace.parent = node.parent;
						node.parent.left = replace;
					} else {
						replace = node.right;
						replace.parent = node.parent;
						node.parent.left = replace;
					}
				}

			}
			size--;
			//replace是替代节点
			afterRemove(replace);
			return;
		}

	}

	// 是否包含元素
	public boolean contains(T element) {

		if (root == null)
			return false;
		Node<T> node = root;
		while (node != null) {
			int reslut = compare(node.element, element);
			if (reslut == 0) {
				return true;
			} else if (reslut > 0) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return false;
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

}
