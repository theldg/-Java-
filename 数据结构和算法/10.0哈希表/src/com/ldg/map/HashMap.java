package com.ldg.map;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import javax.management.ObjectInstance;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import com.ldg.printer.BinaryTreeInfo;
import com.ldg.printer.BinaryTrees;


/**
 * 哈希表实现map 内置数组中存放的事红黑树根节点
 * 
 * @author ldg
 *
 * @param <K>
 * @param <V>
 */
@SuppressWarnings({ "unchecked", "unused" })
public class HashMap<K, V> implements Map<K, V> {
	// 颜色常量
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	// 初始数组容量
	private static final int capacity = 16;
	// 元素数量
	private int size;
	// 内置数组
	private Node<K, V>[] target;

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
		// 获取索引
		int index = indexOfKey(key);
		Node<K, V> root = target[index];
		// 桶元素中位null
		if (root == null) {
			// 创建桶中红黑树的根节点
			root = new Node<K, V>(key, value, null);
			// 桶元素存放根节点
			target[index] = root;
			size++;
			afterPut(root);
			return null;
		} else {

			Node<K, V> node = root;
			// 父节点
			Node<K, V> parent = null;
			// 临时节点
			Node<K, V> tempNode = null;
			int h1 = key == null ? 0 : key.hashCode();

			// 存储比较结果
			int result = 0;
			// 是否已经搜索过这个key
			boolean searched = false;
			// 一直遍历 直到最后一层
			do {
				parent = node;
				int h2 = node.hashCode;
				K key2 = node.key;
				// 哈希值相等 key不一定相等 （key可以是同一类型也可以是不同类型）
				// key相等 哈希值必相等
				if (h1 > h2) {
					result = 1;
				} else if (h1 < h2) {
					result = -1;
				} else if (Objects.equals(key, key2)) {
					// h1=h2
					// key=node.key
					result = 0;
				} else if (key != null && key2 != null && key.getClass() == key2.getClass() && key instanceof Comparable
						&& ((result = ((Comparable) key).compareTo(key2)) != 0)) {
					// h1=h2
					// key!=key2
					// key,key2!=null
					// key和key2是同一类型且该类型具有可比较性
					// 注意:compareTo()==0 时 equal()有可能!=0
					// 也就是说又可能两个不同的key但它们compareTo的结果是相等的
					// 所以当compareTo==0时则进行后面的扫描操作
					// !=0则返回比较结果

				} else if (searched) {
					// 能来到这里说明key与key2只能通过内存哈希值来比较
					// 先判断是否有搜索过
					// searched==true 能来到这表明已经搜索了
					result = System.identityHashCode(key) - System.identityHashCode(key2);
				} else {
					// searched==false 能来到这表明还没有搜索过
					// 则搜索
					if ((tempNode = getNode(key)) == null) {
						// 不存在该 key则比较内存地址
						// 搜索过了
						searched = true;
						result = System.identityHashCode(key) - System.identityHashCode(key2);

					} else {

						// 存在该key
						V oldVelue = tempNode.value;
						tempNode.value = value;
						return oldVelue;

					}
				}

				// 判断新节点在左边还是在右边或是覆盖
				if (result > 0) {

					node = node.right;
				} else if (result < 0) {
					node = node.left;
				} else {
					V oldVelue = node.value;
					node.value = value;
					return oldVelue;
				}
			} while (node != null);
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

	// 通过key获取索引
	private int indexOfKey(K key) {
		if (key == null)
			return 0;
		int h = key.hashCode();
		// 扰动运算
		h = h ^ (h >>> 16);
		return h & (target.length - 1);
	}

	@Override
	public V get(K key) {

		return getNode(key) == null ? null : getNode(key).value;

	}

	// 通过key 层序遍历获取key相同的节点
	private Node<K, V> getNode(K key) {
		Node<K, V> node = target[indexOfKey(key)];
		if (node == null)
			return null;
		Queue<Node<K, V>> queue = new LinkedList<>();
		queue.offer(node);
		do {
			Node<K, V> tempNode = queue.poll();
			if (compareEqual(key, tempNode.key)) {
				return tempNode;
			}
			if (tempNode.left != null) {
				queue.offer(tempNode.left);
			}
			if (tempNode.right != null) {
				queue.offer(tempNode.right);
			}

		} while (!queue.isEmpty());
		return null;

	}

	@Override
	public V remove(K key) {
		Node<K, V> node = getNode(key);
//		System.out.println(node);
		if (node == null)
			return null;
		remove(node);
		return node.value;

	}

	// 移除节点
	private void remove(Node<K, V> node) {
		// 用于取代的节点
		Node<K, V> replace;
		// 桶中的根节点
		Node<K, V> root = target[indexOfKey(node.key)];
//		System.out.println(root);
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
				// 删除的是根节点

				target[indexOfKey(node.key)] = null;
				size--;
				return;
			} else {
				if (node.isLeftNode()) {
					node.parent.left = null;

				} else {
					node.parent.right = null;

				}
				size--;
				// node是被删除的节点
//				System.out.println(node);
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


	// 传入一个参数的afterRemove方法   该参数既可以是被删除节点也可以是替代节点
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
					sibling = parent.left;
					

				}
				changeColor(sibling, colorOf(parent));
				black(parent);
				black(sibling.left);
				rotateRight(parent);

			}

		} else {
			// 黑色叶子节点是左节点


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
					sibling = parent.right;
					

				}
				changeColor(sibling, colorOf(parent));
				black(parent);
				black(sibling.right);
				rotateleft(parent);

			}
		}

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

	@Override
	public void clear() {

		for (int i = 0; i < target.length; i++) {
			target[i] = null;
		}
		size = 0;
	}

	@Override
	public boolean containsKey(K key) {
		return getNode(key) != null;
	}

	@Override
	public boolean containsValue(V value) {
		Queue<Node<K, V>> queue = new LinkedList<>();
		for (int i = 0; i < target.length; i++) {
			Node<K, V> node = target[i];
			if (node == null)
				continue;

			queue.offer(node);
			do {
				Node<K, V> tempNode = queue.poll();
				if (tempNode.value == null) {

					if (value == null)
						return true;

				} else {
					if (tempNode.equals(value))
						return true;

				}

				if (tempNode.left != null) {
					queue.offer(tempNode.left);
				}

				if (tempNode.right != null) {
					queue.offer(tempNode.right);
				}

			} while (!queue.isEmpty());

		}
		return false;
	}

	@Override
	public void traversal(Visitor<K, V> visitor) {

		for (int i = 0; i < target.length; i++) {
			final Node<K, V> node = target[i];
			if (node != null)
				System.out.println("数组序号: " + i);
			BinaryTrees.print(new BinaryTreeInfo() {

				@Override
				public Object string(Object node) {
					// TODO Auto-generated method stub
					return node;
				}

				@Override
				public Object root() {
					// TODO Auto-generated method stub
					return node;
				}

				@Override
				public Object right(Object node) {
					// TODO Auto-generated method stub
					return node == null ? null : ((Node<K, V>) node).right;
				}

				@Override
				public Object left(Object node) {
					// TODO Auto-generated method stub
					return node == null ? null : ((Node<K, V>) node).left;
				}
			});
			System.out.println();
		}

	}

	private static class Node<K, V> {

		// 默认是红色
		private boolean color = RED;

		K key;
		V value;
		Node<K, V> parent;
		Node<K, V> left;
		Node<K, V> right;
		int hashCode;

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

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "[" + key + ":" + value + " " + (color == RED ? "R" : "H") + "]";
		}

		public Node(K key, V value, Node<K, V> parent) {
			this.key = key;

			this.hashCode = key == null ? 0 : key.hashCode();
			this.value = value;
			this.parent = parent;
		}

	}

	// 提供子类一个创建节点的方法
	private Node<K, V> createNode(K key, V value, Node<K, V> parent) {

		return new Node<K, V>(key, value, parent);
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
			target[indexOfKey(grand.key)] = parent;

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

	public HashMap() {
		target = new Node[capacity];
	}

	// 判断两个key是否相等
	private boolean compareEqual(K k1, K k2) {

		return Objects.equals(k1, k2);
	}

}
