package com.ldg.stack;

import com.ldg.ArryList;


public class  Stack <T> {
	
	private   ArryList<T> iList=new  ArryList<T>();
	
	private   int  size;
	
	public   void   push(T element)
	{
		iList.add(element);
	}
	
	public   T   peek()
	{
		size=iList.getLength();
		return  iList.get(size-1);
	}
     
	public    T   remove() {
		
		size=iList.getLength();
		T temp=peek();
		iList.remove(size-1);
		
		return temp ;
		
	}
}
