package com.store.entity;

public class Ingredient {
	
	//attributes
	private Integer id;
	private String name;
	private double quantity;
	private String isFruit;
//	private double density;//densidad = g/ml ... g/cm3
//	private double gPer100ml;
	
	//constructor
	

	public Ingredient() {
		
	}
	
	public Ingredient(Integer id, String name, double quantity, String isFruit) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.isFruit = isFruit;
//		this.gPer100ml = gPer100ml;
	}

	//getters and setters
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

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getIsFruit() {
		return isFruit;
	}

	public void setIsFruit(String isFruit) {
		this.isFruit = isFruit;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + ", quantity=" + quantity + ", isFruit=" + isFruit + "]";
	}

//	public double getDensity() {
//		return density;
//	}
//
//	public void setDensity(double density) {
//		this.density = density;
//	}

//	public double getgPer100ml() {
//		return gPer100ml;
//	}
//
//	public void setgPer100ml(double gPer100ml) {
//		this.gPer100ml = gPer100ml;
//	}



}
