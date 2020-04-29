package com.ldg.set;

import com.ldg.tree.BinaryTree.Visitor;
import com.ldg.tree.RBTree;

public class TreeSet<T> implements Set<T> {
	private RBTree<T> rb = new RBTree<>();

	@Override
	public int size() {
		return rb.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return rb.size() == 0;
	}

	@Override
	public void clear() {
		rb.clear();
	}

	@Override
	public boolean contains(T element) {
		// TODO Auto-generated method stub
		return rb.contains(element);
	}

	@Override
	public void add(T element) {
		// TODO Auto-generated method stub
		rb.add(element);

	}

	@Override
	public void remove(T element) throws Exception {
		// TODO Auto-generated method stub
		rb.remove(element);

	}

	@Override
	public void traversal(Vistor<T> vistor) {
		rb.inorderTraversal(new Visitor<T>() {

			@Override
			public boolean vistor(T element) {
				return vistor.visit(element);

			}
		});

	}

}
