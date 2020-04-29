package com.ldg.circleDeque;

/**
 * 循环双端队列:底层用优化后的动态数组实现 ,可以在首部和尾部进行入队，出队的操作。
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class CircleDeque<T> {

	// 首部序号
	private int frist;
	private int size;
	// 数组初始长度
	private final static int length = 10;
	// 声明一个泛型数组
	@SuppressWarnings("unused")
	private T[] elements;
    // 初始化数组
	public CircleDeque() {

		elements = (T[]) new Object[length];
	}
    //获取队列长度
	public int size() {

		return size;
	}
    //尾部入队
	public void enQueueRear(T element) {

		isAddCapcity();
		// elements[(size + frist) % elements.length] = element;
		elements[index(size)] = element;
		size++;

	}

	//首部入队
	public void enQueueFront(T element) {

		isAddCapcity();
		// 首部元素序号为0参考
		frist = index(-1);
		elements[frist] = element;
		size++;

	}
    //尾部出队
	public T deQueueRear() {

		T old = elements[index(size - 1)];
		elements[index(size - 1)] = null;
		size--;

		return old;

	}
    //首部出队
	public T deQueueFront() {

		T old = elements[index(0)];
		elements[index(0)] = null;
		frist = index(1);
		size--;
		return old;
	}
    //判断是队列是否为空
	public boolean isEmpty() {

		return size() == 0;
	}
    //查看首部元素
	public T front() {

		return elements[index(0)];
	}
    //查看尾部元素
	public T rear() {

		return elements[index(size - 1)];
	}
	// 清空队列
		public void clear() {

			for (int i = 0; i < size; i++) {
				elements[i] = null;

			}
			size = 0;
			frist = 0;
		}
	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("Size:" + size() + " frist" + frist + " CircleDeQueue[");
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

		if (index < 0) {
			// 当在首部入队时要考虑的
			return frist > 0 ? frist + index : index + elements.length;
		} else {
			// 其他情况
			// 取模运算可以优化一下
			// return (frist+index)%elements.length;
			// 因为 0<=length<2elements.length
			int length = frist + index;
			return length >= elements.length ? length - elements.length : length;
		}
	}
}
