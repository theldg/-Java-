package com.ldg.queue;

import com.ldg.doubles.LinkedList;

/***
 * 队列:首部执行出队 尾部执行入队 只在首尾进行操作 所以底层用双向链表来实现 时间复杂度很小
 * 
 * @author Administrator
 *
 */
public class Queue<T> {

	// 双向链表实现
	private LinkedList<T> linkedList = new LinkedList<>();

	// 入队
	public void enQueue(T element) {
		linkedList.add(element);
	}

	// 出队
	public T deQueue() {
		return linkedList.remove(0);
	}

	// 判断是队列否为空
	public Boolean isEmpty() {
		return size() == 0;
	}

	// 队列大小
	public int size() {
		return linkedList.getSize();
	}

	// 获取首部元素
	public T front() {
		return linkedList.get(0);
	}

	// 清空队列
	public void clear() {
		linkedList.clear();
	}

	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("Size:" + size() + " Queue[");
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
