package com.ldg;

//抽取出动态数组和链表的接口
public interface IList<T> {

	/**
	 * 在最后添加元素
	 * 
	 * @param element
	 */
	public void add(T element);

	/**
	 * 根据索引移除元素
	 * 
	 * @param index
	 * @return 被移除前数据
	 */
	public T remove(int index);

	/**
	 * 根据索引更改元素
	 * 
	 * @param index
	 * @param element
	 * @return 被改前数据
	 */
	public T set(int index, T element);

	/**
	 * 清除数组
	 */
	public void clear();

	/**
	 * 根据索引获得元素
	 * 
	 * @param index
	 * @return
	 */

	public T get(int index);

	/**
	 * 
	 * @param index
	 * @param element
	 * @return 原始数据
	 */
	public T add(int index, T element);

	/**
	 * 
	 * @param element
	 * @return 返回出现该数据的第一个索引
	 */
	public int indexOf(T element);
	
	
}