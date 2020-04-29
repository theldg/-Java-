package com.ldg.set;

import com.ldg.doubles.LinkedList;

public class ListSet<T> implements Set<T> {

	private LinkedList<T> list = new LinkedList<>();

	@Override
	public int size() {

		return list.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return list.size() == 0;
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean contains(T element) {

		return list.indexOf(element) == list.ELEMENT_NOT_FOUND;

	}

	@Override
	public void add(T element) {

		if (list.indexOf(element) == list.ELEMENT_NOT_FOUND)
			list.add(element);
		else
			return;

	}

	@Override
	public void remove(T element) throws Exception {

		if (element == null) {
			throw new Exception("参数不能为空");
		} else {
			list.remove(list.indexOf(element));
		}

	}

	@Override
	public void traversal(Vistor<T> vistor) {

		for (int i = 0; i < list.size(); i++) {
			if (vistor.visit(list.get(i)))
				return;
		}
	}

}
