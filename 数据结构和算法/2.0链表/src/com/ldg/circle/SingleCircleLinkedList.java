package com.ldg.circle;





import com.ldg.AbstractList;



//单向循环链表类
//相对与单链表只需要修改add remove部分  其他的通用
public class SingleCircleLinkedList<T> extends AbstractList<T>{
	// 指的是首节点 由首节点指向链表
	private Node<T> first;

	// 声明节点类
	@SuppressWarnings("hiding")
	private class Node<T> {

		T element;
		Node<T> next;

		public Node(T element, Node<T> next) {

			this.element = element;
			this.next = next;
		}

	}

	/**
	 * 根据序号返回节点 从首节点一个个next
	 * 
	 * @param index
	 * @return
	 */
	public Node<T> node(int index) {
		// 判断序号是否越界
		isOutSize(index);
		Node<T> node = first;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}

		return node;
	}

	/**
	 * 根据索引添加元素 返回原始值 注意index=0 index=size index=size-1的边界情况
	 * 
	 */
	@Override
	public T add(int index, T element) {

		T old;
		// 改成循环链表时修改if这部分,else可以继续使用.
		//是不是index=0 添加
		if (index == 0) {
			//声明新节点
			Node<T> node = new Node<T>(element, first);
			//判断：新节点是不是第一个节点
			if (first == null) 
			{
				old = null;
				first = node;
				node.next = first;

			} else {
				old = first.element;
				node(size - 1).next = node;
				first = node;

			}

		} else {

			Node<T> node = node(index - 1).next;
			if (node == null) {
				old = null;
			} else {
				old = node.element;
			}
            
			node(index - 1).next = new Node<T>(element, node(index - 1).next);

		}
		size++;
		return old;
	}

	@Override
	public void add(T element) {
		add(size, element);
	}

	@Override
	public T remove(int index) {
		isOutSize(index);
		T old;
		//删除第一节点
		if (index == 0) 
		{

			old = first.element;
			if (size == 1) {
				first = null;
			} else {
				//最后一个节点指向第一个节点
				node(size - 1).next = first.next;
				//first指向第一个节点
				first = first.next;
			}

		} else {
			old = node(index).element;
			node(index - 1).next = node(index - 1).next.next;
		}
		size--;
		return old;
	}

	@Override
	public T set(int index, T element) {

		T old = node(index).element;
		node(index).element = element;
		return old;
	}

	@Override
	public void clear() {
		size = 0;
		first = null;

	}

	@Override
	public T get(int index) {

		return node(index).element;
	}

	@Override
	public int indexOf(T element) {
		Node<T> node = first;
		for (int i = 0; i < size; i++) {
			if (node.element.equals(element)) {

				return i;
			}
			node = node.next;
		}
		return ELEMENT_NOT_FOUND;
	}

	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Size:" + size + " LinkedList[");
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				stringBuilder.append(",");
			}
			stringBuilder.append(node(i).element);
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	/***
	 * 反转链表
	 * 
	 * @param head
	 * @return
	 */
//	public Node<T> reverseList(Node<T> head) {
//
//		if (head == null || head.next == null)
//			return head;
//		// 与当前链表关联 这样才不会出现空指针
//		first = reverseList(head.next);
//		head.next.next = head;
//		head.next = null;
//
//		return first;
//
//	}
	// 判断是否为循坏链表
	public boolean hasCycle(Node<T> head) {
		if (head == null)
			return false;
		Node<T> slowListNode = head;
		Node<T> fastListNode = head.next;
		while (fastListNode != null && fastListNode.next != null) {
			slowListNode = slowListNode.next;
			fastListNode = fastListNode.next.next;
			if (slowListNode == fastListNode) {
				return true;
			}
		}
		return false;
	}

}
