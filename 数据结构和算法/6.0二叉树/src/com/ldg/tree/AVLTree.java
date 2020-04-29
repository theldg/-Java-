package com.ldg.tree;

public class AVLTree<T> extends BinarySearchTree<T> {

	// 重写afterAdd方法
	protected void afterAdd(Node<T> node) {
		
		// 添加节点的父节点不会失衡 所以只要判断是否为空
		// 从祖父节点开始 后续的祖先节点才有可能失衡

		while ((node = node.parent) != null) {
			if (isBalance(node)) {
				// 更新节点高度 从父节点开始更新高度
				updateHeight(node);

			} else {

				// 恢复平衡
				// 且只要这个最近的节点恢复了平衡 其他节点自然就恢复平衡了
				reBalance2(node);
				break;
			}
		}

	}

    // 重写 afterRemove方法
	@Override
	protected void afterRemove(Node<T> node) {
		
		while((node=node.parent)!=null)
		{
			if(isBalance(node))
			{
				updateHeight(node);
			}else {
				reBalance(node);
				
			}
		}
	
	}
	// 声明AVL节点类
	private class AVLNode<T> extends Node<T> {

		public AVLNode(T element, Node<T> parent) {
			super(element, parent);

		}

		// 节点的高度属性
		int height = 1;

		// 平衡因子
		public int balanceFactory() {
			int leftHeight = (left == null) ? 0 : ((AVLNode) left).height;
			int rightHeight = (right == null) ? 0 : ((AVLNode) right).height;

			return Math.abs(leftHeight - rightHeight);
		}

		// 更新当前节点高度
		public void updateHeight() {
			int leftHeight = (left == null) ? 0 : ((AVLNode) left).height;
			int rightHeight = (right == null) ? 0 : ((AVLNode) right).height;

			height = Math.max(leftHeight, rightHeight) + 1;

		}

		// 获取左右节点中最高的节点
		public Node<T> tallerChildNode() {
			int leftHeight = (left == null) ? 0 : ((AVLNode) left).height;
			int rightHeight = (right == null) ? 0 : ((AVLNode) right).height;

			if (leftHeight == rightHeight) {
				return isLeftNode() ? left : right;
			}

			return (leftHeight > rightHeight) ? left : right;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			if (parent != null)
				return element + "(h:" + height + " p:" + parent.element + ")";
			return element + "(h:" + height + " p:" + null + ")";
		}

	}

	// 重写createNode方法 这样创造的每个节点都是avlnode
	protected Node<T> createNode(T element, Node<T> parent) {
		return new AVLNode(element, parent);
	}

	// 判断某节点是否平衡
	private boolean isBalance(Node<T> node) {

		return ((AVLNode) node).balanceFactory() <= 1;

	}

	// 更新节点高度
	private void updateHeight(Node<T> node) {
		((AVLNode) node).updateHeight();
	}

	// 恢复平衡 使用左旋或右旋
	private void reBalance(Node<T> node) {
		// 获取祖父节点
		Node<T> grand = node;
		// 获取父节点
		Node<T> parent = ((AVLNode) grand).tallerChildNode();
		// 获取子节点
		Node<T> child = ((AVLNode) parent).tallerChildNode();
		if (parent.isLeftNode())// L
		{
			if (child.isLeftNode())// LL 单旋
			{
				rotateRight(grand);

			} else {// LR 双旋
				rotateleft(parent);
				rotateRight(grand);
			}
		} else {

			if (child.isLeftNode())// RL 双旋
			{
				rotateRight(parent);
				rotateleft(grand);

			} else {// RR 单旋

				rotateleft(grand);
			}

		}

	}

	// 恢复平衡 统一操作
	private void reBalance2(Node<T> node) {
		// 获取祖父节点
		Node<T> grand = node;
		// 获取父节点
		Node<T> parent = ((AVLNode) grand).tallerChildNode();
		// 获取子节点
		Node<T> child = ((AVLNode) parent).tallerChildNode();
		if (parent.isLeftNode())// L
		{
			if (child.isLeftNode())// LL 单旋
			{
				rotateAll(grand, child.left, child, child.right, parent, parent.right, grand, grand.right);

			} else {// LR 双旋
				rotateAll(grand, parent.left, parent, child.left, child, child.right, grand, grand.right);
			}
		} else {

			if (child.isLeftNode())// RL 双旋
			{
				rotateAll(grand, grand.left, grand, child.left, child, child.right, parent, parent.right);

			} else {// RR 单旋

				rotateAll(grand, grand.left, grand, parent.left, parent, child.left, child, child.right);
			}

		}

	}

	/**
	 * 
	 * @param r 这棵子树的根节点
	 * @param a ~ @param g 是这棵子树从小到大的节点
	 * 
	 */
	private void rotateAll(Node<T> r, Node<T> a, Node<T> b, Node<T> c, Node<T> d, Node<T> e, Node<T> f, Node<T> g) {
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
		
		//维护三个节点高度
		updateHeight(b);
		updateHeight(f);
		updateHeight(d);

	}

	// 无论是左旋或是右旋 始终是针对两个节点(一个祖父节点和一个父节点)来进行操作的
	// 至于child节点是可有可无的。
	// 右旋转
	private void rotateRight(Node<T> grand) {
		// 右旋转
		Node<T> parent = grand.left;
		Node<T> child = parent.right;
		grand.left = child;
		parent.right = grand;
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

		// 依次更新高度 从低节点到高节点
		updateHeight(grand);
		updateHeight(parent);
	}

	// 左旋转
	private void rotateleft(Node<T> grand) {
		// 左旋转
		Node<T> parent = grand.right;
		Node<T> child = parent.left;
		grand.right = child;
		parent.left = grand;

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
		// 维护child的父节点 child 不确定 也可以为空
		if (child != null) {
			child.parent = grand;
		}

		// 依次更新高度 从第节点到高节点
		updateHeight(grand);
		updateHeight(parent);
	}

}
