package com.ldg.tree;


/**
 * AVL树
 * @author ldg
 *
 * @param <T>
 */
public class AVLTree<T> extends BalanceBinarySearchTree<T> {
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
	protected void afterRemove(Node<T> node) {

		while ((node = node.parent) != null) {
			if (isBalance(node)) {
				updateHeight(node);
			} else {
				reBalance(node);

			}
		}

	}

	// 判断某节点是否平衡
	private boolean isBalance(Node<T> node) {

		return ((AVLNode) node).balanceFactory() <= 1;

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

	}// 恢复平衡 统一操作

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

	// 重写父类统一旋转方法
	@Override
	protected void rotateAll(Node<T> r, Node<T> a, Node<T> b, Node<T> c, Node<T> d, Node<T> e, Node<T> f, Node<T> g) {
		// TODO Auto-generated method stub
		super.rotateAll(r, a, b, c, d, e, f, g);

		// 维护三个节点高度
		updateHeight(b);
		updateHeight(f);
		updateHeight(d);

	}

	@Override
	protected void afterRolate(Node<T> child, Node<T> grand, Node<T> parent) {

		super.afterRolate(child, grand, parent);
		// 依次更新高度 从第节点到高节点
		updateHeight(grand);
		updateHeight(parent);
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

	// 更新节点高度
	private void updateHeight(Node<T> node) {
		((AVLNode) node).updateHeight();
	}

}
