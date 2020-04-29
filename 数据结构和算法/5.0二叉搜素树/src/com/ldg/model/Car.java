package com.ldg.model;

public class Car {

	private int price;

	@Override
	public String toString() {

		return "car :[ price :" + price + "]";
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Car() {

	}

	public Car(int price) {
		this.price = price;
	}
}
