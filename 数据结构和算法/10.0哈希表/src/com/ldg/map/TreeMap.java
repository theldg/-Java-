package com.ldg.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;


/**
 * 红黑树实现映射一:直接声明可以存放键值对的红黑树 必须具备比较性或者实现比较器
 *
 * @author ldg
 *
 * @param <K>
 * @param <V>
 */
@SuppressWarnings({ "unused", "unchecked" })

public class TreeMap<K, V> implements Map<K, V> {
	// 颜色常量
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	// 元素数量
	private int size;
	// 根节点
	private Node<K, V> root;
	// 声明比较器
	private Comparator<K> comparator;

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public V put(K key, V value) {

		// 判断是否有根节点
		if (root == null) {
			root = createNode(key, value, null);
			size++;
			afterPut(root);
			return null;
		} else {
			Node<K, V> node = root;
			Node<K, V> parent = null;

			int result = 0;
			// 一直遍历 直到最后一层
			while (node != null) {
				parent = node;
				result = compare(key, node.key);
				if (result > 0) {

					node = node.right;
				} else if (result < 0) {
					node = node.left;
				} else {
					V oldVelue = node.value;
					node.value = value;
					return oldVelue;
				}
			}
			// 根据结果判断是添加到右子树还是左子树
			Node<K, V> newNode = createNode(key, value, parent);
			if (result > 0) {

				parent.right = newNode;
				size++;

			} else {
				parent.left = newNode;
				size++;

			}
			afterPut(newNode);
			return parent.value;
		}
	}
	// 添加元素后要进行的操作

	private void afterPut(Node<K, V> node) {
		// 添加的是根节点
		if (node.parent == null) {
			black(node);

			return;
		}
		// 父节点是黑色
		if (isBlack(node.parent)) {

			return;
		} else {// 父节点不是黑色
			// 祖父节点
			Node<K, V> grand = node.parent.parent;
			// 父节点
			Node<K, V> parent = node.parent;
			// 叔父节点是红色
			if (isRed(node.parent.getSibling())) {
				black(parent);
				black(parent.getSibling());
				afterPut(red(grand));
				return;
			}
			// 叔父节点不是红色
			// L
			if (node.parent.isLeftNode()) {
				// LL
				red(grand);

				if (node.isLeftNode()) {
					black(node.parent);

				} else {// LR
					black(node);
					rotateleft(parent);

				}
				rotateRight(grand);
			} else {// R
				red(grand);

				// RL
				if (node.isLeftNode()) {
					black(node);

					rotateRight(parent);
					rotateleft(grand);
				} else {// RR

					black(parent);
					rotateleft(grand);

				}

			}

		}

	}

	@Override
	public V remove(K key) {
		Node<K, V> node = getNode(key);
		if (node == null)
			return null;
		remove(node);
		return node.value;

	}

	@Override
	public boolean containsKey(K key) {

		return getNode(key) != null;
	}

	@Override
	public boolean containsValue(V value) {
		if (root == null) {
			return false;
		}
		Queue<Node<K, V>> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {

			Node<K, V> node = queue.poll();
			if (valEqual(value, node.value)) {
				return true;
			}

			if (node.left != null) {
				queue.offer(node.left);
			}

			if (node.right != null) {
				queue.offer(node.right);
			}

		}
		return false;
	}

	private boolean valEqual(V value1, V value2) {
		return value1 == null ? value1 == value2 : value1.equals(value2);
	}

	@Override
	public void traversal(Visitor<K, V> visitor) {
		if (root == null)
			return;

		traversal(root, visitor);

	}

	private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
		if (node == null || visitor.flag)
			return;
		traversal(node.left, visitor);
		if (!visitor.flag)
			visitor.flag = visitor.visit(node.key, node.value);
		traversal(node.right, visitor);

	}

	private static class Node<K, V> {
		// 默认是红色
		private boolean color = RED;

		K key;
		V value;
		Node<K, V> parent;
		Node<K, V> left;
		Node<K, V> right;

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

		// 获取兄弟节点
		public Node<K, V> getSibling() {

			if (isLeftNode()) {
				return parent.right;
			} else if (isRightNode()) {
				return parent.left;
			} else {
				return null;
			}
		}

		public Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}

	}

	// 提供子类一个创建节点的方法
	private Node<K, V> createNode(K key, V value, Node<K, V> parent) {

		return new Node<K, V>(key, value, parent);
	}

	// 比较方法

	private int compare(K element, K element2) {
		// 判断是否有传入比较器
		if (comparator != null)
			// 具体比较要看传入的比较器
			return comparator.compare(element, element2);
		else
			// 没有传入比较器就用元素自己的默认比较方法比较 如果元素不具有比较性就会报错
			return ((Comparable<K>) element).compareTo(element2);

	}

	/**
	 * 判断节点的颜色 空节点默认是黑色的
	 * 
	 * @param node
	 * @return false：黑色 ， true：红色
	 */
	private boolean colorOf(Node<K, V> node) {
		return node == null ? BLACK : node.color;
	}

	/**
	 * 判断节点是否为黑色
	 * 
	 * @param node
	 * @return
	 */
	private boolean isBlack(Node<K, V> node) {
		return colorOf(node) == BLACK;
	}

	/**
	 * 判断节点是否为红色
	 * 
	 * @param node
	 * @return
	 */
	private boolean isRed(Node<K, V> node) {
		return colorOf(node) == RED;
	}

	/**
	 * 改变节点的颜色
	 * 
	 * @param node
	 * @param color
	 * @return 返回改变后的节点
	 */
	private Node<K, V> changeColor(Node<K, V> node, boolean color) {
		if (node != null)
			node.color = color;
		return node;
	}

	/**
	 * 将节点变成黑色
	 * 
	 * @param node
	 * @return
	 */
	private Node<K, V> black(Node<K, V> node) {
		return changeColor(node, BLACK);
	}

	/**
	 * 将节点变成红色
	 * 
	 * @param node
	 * @return
	 */
	private Node<K, V> red(Node<K, V> node) {
		return changeColor(node, RED);
	}

	// 无论是左旋或是右旋 始终是针对两个节点(一个祖父节点和一个父节点)来进行操作的
	// 至于child节点是可有可无的。
	// 右旋转
	private void rotateRight(Node<K, V> grand) {
		// 右旋转
		Node<K, V> parent = grand.left;
		Node<K, V> child = parent.right;
		grand.left = child;
		parent.right = grand;
		// 旋转后需要进行的操作
		afterRolate(child, grand, parent);

	}

	/**
	 * //旋转后需要进行的操作
	 * 
	 * @param child
	 * @param grand
	 * @param parent
	 */
	private void afterRolate(Node<K, V> child, Node<K, V> grand, Node<K, V> parent) {
		// 维护parent的父节点
		parent.parent = grand.parent;
		if (grand.isLeftNode()) {
			grand.parent.left = parent;

		} else if (grand.isRightNode()) {
			grand.parent.right = parent;

		} else {
			// grand.parent==null
			root = parent;

		}
		// 维护grand的父节点
		grand.parent = parent;
		// 维护child的父节点
		if (child != null) {
			child.parent = grand;
		}

	}

	// 左旋转
	private void rotateleft(Node<K, V> grand) {
		// 左旋转
		Node<K, V> parent = grand.right;
		Node<K, V> child = parent.left;
		grand.right = child;
		parent.left = grand;

		afterRolate(child, grand, parent);

	}

	// 传入一个参数的afterRemove方法 该参数既可以是被删除节点也可以是替代节点
	// 删除度为2的节点会去删除它的前驱或后继节点
	// 所以其实真正被删除的节点只有可能是度为0或1的节点
	protected void afterRemove(Node<K,V> node) {
		// 被删除节点的父节点
		Node<K,V> parent = node.parent;

		// 被删除的节点是根节点
		if (parent == null)
			return;

		// 如果替代节点是红色或者被删除的节点是红色
		if (isRed(node)) {
			black(node);
			return;
		}

		// 被删除的节点是黑色叶子节点

		// 判断被删除的节点是左节点还是右节点
		boolean left = parent.left == null || node.isLeftNode();
		// 获取兄弟节点
		Node<K,V> sibling = left ? parent.right : parent.left;

		// 黑色叶子节点的两种可能代码是对称的
		// 黑色叶子节点是右节点
		if (!left) {

			// 兄弟节点是红色
			if (isRed(sibling)) {

				red(parent);
				black(sibling);

				rotateleft(parent);
				// 更换兄弟节点
				sibling = parent.right;
			}

			// 兄弟节点是黑色

			// 兄弟节点没有一个红色子节点
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				// 父节点的颜色是否为黑色
				boolean parentColor = isBlack(parent);
				// 将父节点变成黑色
				black(parent);
				// 将兄弟节点变成红色
				red(sibling);
				if (parentColor) {
					afterRemove(parent);
					return;
				}

			} else {
				// 兄弟节点至少有一个红色子节点

				// 兄弟节点的左子节点是黑色
				if (isBlack(sibling.right)) {

					rotateRight(sibling);
					sibling = parent.left;

				}
				changeColor(sibling, colorOf(parent));
				black(parent);
				black(sibling.right);
				rotateleft(parent);

			}

		} else {
			// 黑色叶子节点是左节点

			// 兄弟节点是红色
			if (isRed(sibling)) {

				red(parent);
				black(sibling);

				rotateRight(parent);
				// 更换兄弟节点
				sibling = parent.left;
			}

			// 兄弟节点是黑色

			// 兄弟节点没有一个红色子节点
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				// 父节点的颜色是否为黑色
				boolean parentColor = isBlack(parent);
				// 将父节点变成黑色
				black(parent);
				// 将兄弟节点变成红色
				red(sibling);
				if (parentColor) {
					afterRemove(parent);
					return;
				}

			} else {
				// 兄弟节点至少有一个红色子节点

				// 兄弟节点的左子节点是黑色
				if (isBlack(sibling.left)) {

					rotateleft(sibling);
					sibling = parent.right;

				}
				changeColor(sibling, colorOf(parent));
				black(parent);
				black(sibling.left);
				rotateRight(parent);

			}

		}

	}

	// 移除节点
	private void remove(Node<K, V> node) {
		// 用于取代的节点
		Node<K, V> replace;
		// 删除节点度为2的节点
		if (node.hasTwoChildren()) {
			// 直接将它的前驱节点的值赋给该节点 然后删除前驱节点
			node.key = getpredecessor(node).key;
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
			// replace是替代节点
			afterRemove(replace);
			return;
		}

	}

	// 根据元素找到节点
	private Node<K, V> getNode(K element) {
		Node<K, V> node = root;
		while (node != null) {
			int result = compare(node.key, element);
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

	// 获取某个节点的前驱节点
	private Node<K, V> getpredecessor(Node<K, V> node) {
		if (node == null)
			return null;
		if (node.left != null) {
			Node<K, V> tempNode = node.left;
			while (tempNode.right != null) {
				tempNode = tempNode.right;
			}

			return tempNode;
		} else {
			if (node.parent == null) {
				return null;
			} else {
				{
					Node<K, V> tempNode = node.parent;
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
	private Node<K, V> getSuccessor(Node<K, V> node) {
		if (node == null)
			return null;
		if (node.right != null) {
			Node<K, V> tempNode = node.right;
			while (tempNode.left != null) {
				tempNode = tempNode.left;
			}

			return tempNode;
		} else {
			if (node.parent == null) {
				return null;
			} else {
				{
					Node<K, V> tempNode = node.parent;
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

	public TreeMap() {

	}

	public TreeMap(Comparator<K> comparator) {
		this.comparator = comparator;
	}

	@Override
	public V get(K key) {
		if (key == null)
			return null;

		return getNode(key) == null ? null : getNode(key).value;
	}

	@Override
	public void clear() {

		root = null;
		size = 0;

	}
}
