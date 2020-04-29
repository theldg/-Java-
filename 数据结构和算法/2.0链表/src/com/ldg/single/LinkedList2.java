package com.ldg.single;

import java.util.IllegalFormatCodePointException;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import com.ldg.AbstractList;

@SuppressWarnings("unused")
//单向链表类
//增加一个虚拟节点 使代码简洁
public class LinkedList2<T> extends AbstractList<T> {
	// 指的是首节点 由首节点指向链表
	// 虚拟节点
	private Node<T> first = new Node<T>(null, null);

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

	@Override
	public void add(T element) {
		add(size, element);
	}

	@Override
	public T remove(int index) {
		isOutSize(index);
		T old;

		old = node(index).element;
		node(index - 1).next = node(index - 1).next.next;

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

	/**
	 * 根据索引添加元素 返回原始值 注意index=0 index=size index=size-1的边界情况
	 */
	@Override
	public T add(int index, T element) {

		T old;

		if (index == 0) {
			if (first.next == null) {
				old = null;

			}else {
				old=first.next.element;
			}
			first.next = new Node<T>(element, first.next);

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
	public int indexOf(T element) {
		Node<T> node = first.next;
		for (int i = 0; i < size; i++) {
			if (node.element.equals(element)) {

				return i;
			}
			node = node.next;
		}
		return ELEMENT_NOT_FOUND;
	}

	public Node<T> node(int index) {
		isOutSize(index);
		Node<T> node = first.next;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}

		return node;
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
	public Node<T> reverseList(Node<T> head) {

		if (head == null || head.next == null)
			return head;
		// 与当前链表关联 这样才不会出现空指针
		first.next= reverseList(head.next);
		head.next.next = head;
		head.next = null;

		return first.next;
	}

}
