package com.store.entity;

public class Cup {
	
	private Integer id;
	private String name;
	private double mlSize;
	private double price;
	
	public Cup() {}

	public Cup(Integer id, String name, double mlSize, double price) {
		this.id = id;
		this.name = name;
		this.mlSize = mlSize;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMlSize() {
		return mlSize;
	}

	public void setMlSize(double mlSize) {
		this.mlSize = mlSize;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
