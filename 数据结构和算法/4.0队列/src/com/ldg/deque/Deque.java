package com.ldg.deque;

import com.ldg.doubles.LinkedList;

/**
 * 双端队列:在首尾都可以执行出队和入队 双向链表实现
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class Deque<T> {

	// 双向链表
	private LinkedList<T> linkedList = new LinkedList<>();

	// 队列大小
	public int size() {

		return linkedList.getSize();
	}

	// 从尾部入队
	public void enQueueRear(T element) {

		linkedList.add(element);
	}

	// 从首部入队
	public void enQueueFront(T element) {

		linkedList.add(0, element);

	}

	// 从尾部出队
	public T deQueueRear() {

		return linkedList.remove(size() - 1);

	}

	// 从首部出队
	public T deQueueFront() {

		return linkedList.remove(0);
	}

	public boolean isEmpty() {

		return size() == 0;
	}

	// 获取首部元素
	public T front() {

		return linkedList.get(0);

	}

	// 获取尾部元素
	public T rear() {

		return linkedList.get(size() - 1);
	}

	// 清空队列
	public void clear() {
		linkedList.clear();

	}

	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("Size:" + size() + " DeQueue[");
		for (int i = 0; i < size(); i++) {
			if (i != 0) {
				stringBuilder.append(",");
			}

			stringBuilder.append(linkedList.get(i));
		}
		stringBuilder.append("]");

		return stringBuilder.toString();

	}
}
