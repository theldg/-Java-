package com.ldg;

public class Car {

	private String name;
	private int price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Car(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}

//重写equals方法
	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null)
			return false;
		if (arg0 instanceof Car) {
			Car car = (Car) arg0;
			return this.price == car.getPrice();
		}
		return false;

	}

}
