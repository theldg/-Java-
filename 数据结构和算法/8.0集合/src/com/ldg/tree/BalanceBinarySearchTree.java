package com.ldg.tree;

/**
  * 平衡二叉搜索树 
  * 在二叉搜索树的基础上增加了旋转这一个功能
 * 
 * @author ldg
 *
 * @param <T>
 */
public class BalanceBinarySearchTree<T> extends BinarySearchTree<T> {

	/**
	 * 
	 * @param r 这棵子树的根节点
	 * @param a ~ @param g 是这棵子树从小到大的节点
	 * 
	 */
	protected void rotateAll(Node<T> r, Node<T> a, Node<T> b, Node<T> c, Node<T> d, Node<T> e, Node<T> f, Node<T> g) {
		d.parent = r.parent;
		// r是父节点的左节点
		if (r.isLeftNode()) {
			r.parent.left = d;
		} else if (r.isRightNode()) // r是父节点的右节点
		{
			r.parent.right = d;
		} else {
			root = d;
		}

		// 重建b这棵树
		b.left = a;
		b.right = c;
		if (a != null)
			a.parent = b;
		if (c != null)
			c.parent = b;
		// 重建f这棵树
		f.left = e;
		f.right = g;
		if (e != null)
			e.parent = f;
		if (g != null)
			g.parent = f;
		// 重建d这棵树
		d.left = b;
		d.right = f;
		b.parent = d;
		f.parent = d;

	}

	// 无论是左旋或是右旋 始终是针对两个节点(一个祖父节点和一个父节点)来进行操作的
	// 至于child节点是可有可无的。
	// 右旋转
	protected void rotateRight(Node<T> grand) {
		// 右旋转
		Node<T> parent = grand.left;
		Node<T> child = parent.right;
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
	protected void afterRolate(Node<T> child, Node<T> grand, Node<T> parent) {
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
	protected void rotateleft(Node<T> grand) {
		// 左旋转
		Node<T> parent = grand.right;
		Node<T> child = parent.left;
		grand.right = child;
		parent.left = grand;

		afterRolate(child, grand, parent);

	}

}
