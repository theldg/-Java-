package com.ldg.set;

import java.util.Comparator;

import com.ldg.map.Map.Visitor;
import com.ldg.map.TreeMap;
/**
  *  利用TreeMap来实现TreeSet 
  *  优点:TreeMap本身就具有去重功能
  *  缺点:TreeMap要求key必须具有可比较性或者实现比较器
 * @author ldg
 *
 * @param <T>
 */
public class TreeSet<T> implements Set<T> {
	private TreeMap<T, Object> treeMap;

	@Override
	public int size() {
		return treeMap.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return treeMap.isEmpty();
	}

	@Override
	public void clear() {

		treeMap.clear();

	}

	@Override
	public boolean contains(T element) {
		return treeMap.containsKey(element);
	}

	@Override
	public void add(T element) {
		treeMap.put(element, null);

	}

	@Override
	public void remove(T element) throws Exception {
		treeMap.remove(element);

	}

	@Override
	public void traversal(Vistor<T> vistor) {
		treeMap.traversal(new Visitor<T, Object>() {

			@Override
			public boolean visit(T key, Object value) {
				vistor.visit(key);
				return false;
			}
		});
	}

	public TreeSet() {

		this(null);

	}

	public TreeSet(Comparator<T> comparator) {
		treeMap = new TreeMap<T, Object>(comparator);
	}

}
