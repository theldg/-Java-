package com.ldg.model;

//声明people类  并且有可比较性
public class People  implements Comparable<People>{
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public  People()
	{
		
	}
	public  People(String  name,int  age)
	{
		this.name=name;
		this.age=age;
	}
	
	//实现比较方法
	@Override
	public int compareTo(People o) {

		return  this.age-o.age;
	}
	
	@Override
	public String toString() {
		
		return " people:[ name:"+name+" age:"+age+" ]";
	}
}
