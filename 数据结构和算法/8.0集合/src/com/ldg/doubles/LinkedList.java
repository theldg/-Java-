package com.ldg.doubles;

import java.util.IllegalFormatCodePointException;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import com.ldg.AbstractList;

@SuppressWarnings("unused")
//双向链表类
public class LinkedList<T> extends AbstractList<T> {
	// 指的是首节点 由首节点指向链表
	private Node<T> first;
	// 尾节点
	private Node<T> last;

	// 声明节点类
	@SuppressWarnings("hiding")
	private class Node<T> {

		T element;
		// 后一个节点
		Node<T> next;
		// 前一个节点
		Node<T> pre;

		public Node(Node<T> pre, T element, Node<T> next) {
			this.pre = pre;
			this.element = element;
			this.next = next;
		}

	}

	/**
	 * 根据序号返回节点 分情况从头或者尾开始移动
	 * 
	 * @param index
	 * @return
	 */
	public Node<T> node(int index) {
		// 判断序号是否越界
		isOutSize(index);
		Node<T> node = null;
		if (index < size >> 2) {
			node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}

		} else {
			node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.pre;
			}
		}
		return node;
	}

	/**
	 * 注意一下节点中两个指针的指向.不要漏忘 根据索引添加元素 返回原始值 注意index=0 index=size index=size-1的边界情况
	 */
	@Override
	public T add(int index, T element) {
		if (element == null)
			return null;
		T old;
		// 直接添加到链表最后面
		if (index == size) {
			old = null;
			Node<T> pre = last;
			Node<T> node = new Node<T>(pre, element, null);
			last = node;
			if (pre == null) // 当index=size=0;
			{
				first = node;
			} else {
				pre.next = node;
			}

		} else {

			Node<T> next = node(index);
			old = next.element;
			Node<T> pre = node(index).pre;
			Node<T> node = new Node<T>(pre, element, next);
			if (pre == null)// index=0
			{
				first = node;
			} else {
				pre.next = node;
			}
			next.pre = node;
		}
		size++;
		return old;
	}

	/**
	 * 添加
	 */
	@Override
	public void add(T element) {
		add(size, element);
	}

	/**
	 * 移除节点
	 */
	@Override
	public T remove(int index) {

		T old = null;
		Node<T> next = node(index).next;
		Node<T> pre = node(index).pre;
		if (pre == null) // index=0的情况
		{

			first = next;
		} else {

			pre.next = next;
		}
		if (next == null) // index=size-1的情况;
		{
			last = pre;
		} else {
			next.pre = pre;
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

	// 如果不是被gc root对象指向的内存地址块会被java回收站机制回收。
	// 就是没局部变量指向的对象。
	@Override
	public void clear() {

		size = 0;
		first = null;
		last = null;

	}

	@Override
	public T get(int index) {

		return node(index).element;
	}

	public int size() {
		return size;

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
		System.out.println(size);
		return ELEMENT_NOT_FOUND;
	}

	// Stringbuilder的拼接
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
		System.out.println(size);
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
		first = reverseList(head.next);
		head.next.next = head;
		head.pre = head.next;
		last = head;

		return first;

	}

}
