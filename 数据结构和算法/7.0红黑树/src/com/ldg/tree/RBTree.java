package com.ldg.tree;

public class RBTree<T> extends BalanceBinarySearchTree<T> {
	// 颜色常量
	private static final boolean RED = true;
	private static final boolean BLACK = false;

	private class RBNode<T> extends Node<T> {
		// 默认是红色
		private boolean color = RED;

		public RBNode(T element, Node<T> parent) {
			super(element, parent);

		}

		@Override
		public String toString() {
			if (parent != null) {

				return element + ":" + (color == true ? "r" : "h") + " p:" + parent.element;

			}
			return element + ":" + (color == true ? "r" : "h") + " p:";

		}

	}

	/**
	 * 判断节点的颜色 空节点默认是黑色的
	 * 
	 * @param node
	 * @return false：黑色 ， true：红色
	 */
	private boolean colorOf(Node<T> node) {
		return node == null ? BLACK : ((RBNode) node).color;
	}

	/**
	 * 判断节点是否为黑色
	 * 
	 * @param node
	 * @return
	 */
	private boolean isBlack(Node<T> node) {
		return colorOf(node) == BLACK;
	}

	/**
	 * 判断节点是否为红色
	 * 
	 * @param node
	 * @return
	 */
	private boolean isRed(Node<T> node) {
		return colorOf(node) == RED;
	}

	/**
	 * 改变节点的颜色
	 * 
	 * @param node
	 * @param color
	 * @return 返回改变后的节点
	 */
	private RBNode<T> changeColor(Node<T> node, boolean color) {
		if (node != null)
			((RBNode) node).color = color;
		return (RBNode) node;
	}

	/**
	 * 将节点变成黑色
	 * 
	 * @param node
	 * @return
	 */
	private RBNode<T> black(Node<T> node) {
		return changeColor(node, BLACK);
	}

	/**
	 * 将节点变成红色
	 * 
	 * @param node
	 * @return
	 */
	private RBNode<T> red(Node<T> node) {
		return changeColor(node, RED);
	}

	// 创建自己的子节点
	@Override
	protected Node<T> createNode(T element, Node<T> parent) {

		return new RBNode(element, parent);
	}

	// 添加元素后要进行的操作
	@Override
	protected void afterAdd(Node<T> node) {
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
			Node<T> grand = node.parent.parent;
			// 父节点
			Node<T> parent = node.parent;
			// 叔父节点是红色
			if (isRed(node.parent.getSibling())) {
				black(parent);
				black(parent.getSibling());
				afterAdd(red(grand));
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

	// 传入一个参数的afterRemove方法   该参数既可以是被删除节点也可以是替代节点
	// 删除度为2的节点会去删除它的前驱或后继节点
	// 所以其实真正被删除的节点只有可能是度为0或1的节点
	protected void afterRemove(Node<T> node) {
		// 被删除节点的父节点
		Node<T> parent = node.parent;

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
		Node<T> sibling = left ? parent.right : parent.left;

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

	// 传入两个参数的afterRemove方法
	// 删除度为2的节点会去删除它的前驱或后继节点
	// 所以其实真正被删除的节点只有可能是度为0或1的节点
	protected void afterRemove1(Node<T> node, Node<T> replace) {
		// 被删除节点的父节点
		Node<T> parent = node.parent;
		// 被删除的节点是红色
		if (isRed(node))
			return;

		// 被删除的节点是根节点
		if (parent == null)
			return;

		// 如果替代节点是红色
		if (isRed(replace)) {
			black(replace);
			return;
		}

		// 被删除的节点是黑色叶子节点

		// 判断被删除的节点是左节点还是右节点
		boolean left = parent.left == null || node.isLeftNode();
		// 获取兄弟节点
		Node<T> sibling = left ? parent.right : parent.left;

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
				//父节点原先颜色是黑色 则将父节点传入afteRemove方法来维持红黑树性质  
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
				//父节点原先颜色是黑色 则将父节点传入afteRemove方法来维持红黑树性质  
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

}
