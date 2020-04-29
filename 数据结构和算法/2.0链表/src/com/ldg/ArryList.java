package com.ldg;

@SuppressWarnings("unchecked")
public class ArryList<T> extends AbstractList<T>  {

	// 数组
	private T[] elements;
	private static final int DEFAULT_CAPACITY = 2;

	/**
	 * 在最后添加元素
	 * 
	 * @param element
	 */
	public void add(T element) {

		add(size, element);

	}

	/**
	 * 根据索引移除元素
	 * 
	 * @param index
	 * @return 被移除前数据
	 */
	public T remove(int index) {
		isOutSize(index);
		T old = elements[index];
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
		elements[size] = null;

		return old;

	}

	/**
	 * 根据索引更改元素
	 * 
	 * @param index
	 * @param element
	 * @return 被改前数据
	 */
	public T set(int index, T element) {
		isOutSize(index);
		T old = elements[index];
		elements[index] = element;
		return old;

	}

	/**
	 * 清除数组
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
  
    
	public T get(int index) {
		isOutSize(index);
		return elements[index];
	}

	/**
	 * 
	 * @param index
	 * @param element
	 * @return 原始数据
	 */
	public T add(int index, T element) {
		// 允许size==index
		isOutSizeForAdd(index);
		enSureAddCapacity(index);
		T old = elements[index];
		elements[index] = element;
		for (int i =size - 1; i > index; i--) {
			elements[i + 1] = elements[i];
		}
		size++;
		return old;
	}

	/**
	 * 
	 * @param element
	 * @return 返回出现该数据的第一个索引
	 */
	public int indexOf(T element) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(element)) {
				return i;
			}
		}
		return ELEMENT_NOT_FOUND;
	}

	public ArryList() {
		// 默认只能存放2个对象
		// 引用泛型会有内存管理问题。
		elements = (T[]) new Object[DEFAULT_CAPACITY];

	}

	public ArryList(int capacity) {
		if (capacity <= 0)
			capacity = 2;
		elements = (T[]) new Object[capacity];
	}

	

	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("size:" + size + " ArryList:").append("[");
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				stringBuilder.append(",");
			}

			stringBuilder.append(elements[i]);
		}
		stringBuilder.append("]");
		return stringBuilder.toString();

	}

	public void enSureAddCapacity(int index) {
		int oldCapacity = elements.length;
		// 位运算符 扩容一倍 左移一位为*2 右移一位为/2
		int newCapacity = oldCapacity << 1;
		if (index == oldCapacity) {

			T[] newElements = (T[]) new Object[newCapacity];
			for (int i = 0; i < size; i++) {
				newElements[i] = elements[i];
			}
			elements = newElements;
			System.out.println(oldCapacity + " 扩容为 " + newCapacity);
		}

	}

}
