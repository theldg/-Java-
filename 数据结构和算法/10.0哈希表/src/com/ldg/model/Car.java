package com.ldg.model;



public class Car {
	private String name;
	private int money;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	@Override
	public int hashCode() {

		return money / 20;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass()==obj.getClass()) {
			if (this.name == ((Car) obj).name && this.money == ((Car) obj).money)
				return true;

		}

		return false;

	}

	public Car(String name, int money) {
		this.name = name;
		this.money = money;
	}
	@Override
	public String toString() {
		return name+"_"+money;
	}

}
