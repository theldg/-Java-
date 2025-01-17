package com.ldg.map;

/**
 * 映射接口
 * 
 * @author ldg
 *
 * @param <K> 键
 * @param <V> 值
 */
public interface Map<K, V> {

	int size();

	boolean isEmpty();

	V put(K key, V value);

	V get(K key);

	V remove(K key);

	void clear();

	boolean containsKey(K key);

	boolean containsValue(V value);

	void traversal(Visitor<K, V> visitor);

	public static abstract class Visitor<K, V> {

		// 是否停止显示
		boolean flag = false;

		// 如何显示一个元素
		public abstract boolean visit(K key, V value);
	}

}
