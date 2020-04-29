package com.ldg.circleQueue;

//循环队列:底层用优化后的动态数组实现 所以有循环的感觉
//         ps:就是在首部出队时不需要将第二个元素到尾部元素一个个往前移动
//         ps:在尾部入队时不一定要一味往后加 ，可以利用数组前面的空间
//这样方法实现的复杂度也是O(1)

@SuppressWarnings("unchecked")
public class CircleQueue<T> {

	// 首部序号
	private int frist;
	private int size;
	// 数组初始长度
	private final static int length = 10;

	// 声明一个泛型数组
	@SuppressWarnings("unused")
	private T[] elements;

	// 初始化数组
	public CircleQueue() {

		elements = (T[]) new Object[length];
	}

	// 入队 (size+frist)%elements.length取模运算可以利用到前面的空间
	public void enQueue(T element) {
		isAddCapcity();
		// elements[(size + frist) % elements.length] = element;
		elements[index(size)] = element;
		size++;
	}

	// 出队 (frist + 1) % elements.length 找到第首部下一个元素
	public T deQueue() {

		T old = elements[index(0)];
		elements[index(0)] = null;
		frist = index(1);
		size--;
		return old;

	}

	// 判断队列是否为空
	public Boolean isEmpty() {

		return size == 0;
	}

	// 获取队列元素个数
	public int size() {

		return size;
	}

	// 查看首部元素的值
	public T front() {

		return elements[frist];
	}

	// 清空队列
	public void clear() {

		for (int i = 0; i < size; i++) {
			elements[i] = null;

		}
		size = 0;
		frist = 0;
	}
	


	// 是将整个数组打印
	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("Size:" + elements.length + " Frist:" + frist + " CircleQueue[");
		for (int i = 0; i < elements.length; i++) {
			if (i != 0) {
				stringBuilder.append(",");
			}

			stringBuilder.append(elements[i]);
		}
		stringBuilder.append("]");

		return stringBuilder.toString();

	}

	// 判断是否扩容
	private void isAddCapcity() {
		if ((size + 1) > elements.length) {
			int newCapcity = elements.length << 1;
			T[] newElemenTs = (T[]) new Object[newCapcity];
			for (int i = 0; i < elements.length; i++) {
				// newElemenTs[i] = elements[(frist + i) % elements.length];
				newElemenTs[i] = elements[index(i)];
			}
			frist = 0;
			elements = newElemenTs;
		}
	}

	// 索引映射:将逻辑索引映射成真实索引 并返回
	private int index(int index) {
		// 取模运算可以优化一下
		// return (frist+index)%elements.length;
		// 因为 0<=length<2elements.length
		int length = frist + index;
		return length >= elements.length ? length - elements.length : length;
	}

}
