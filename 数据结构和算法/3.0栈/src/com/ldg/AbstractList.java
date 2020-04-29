package com.ldg;
//抽象类可以不实现接口的方法
public abstract class AbstractList<T> implements IList<T> {
	// 默认异常返回值 protected 指的是子类可以引用
	protected static final int ELEMENT_NOT_FOUND = -1;
	// 元素个数
	protected int size = 0;

	/**
	 * 判断索引是否合法
	 * 
	 * @param index
	 */
	protected void isOutSize(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException("index:" + index + " size:" + size);
		}
	}
	protected void isOutSizeForAdd(int index) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("index:" + index + " size:" + size);
		}
	}

}
