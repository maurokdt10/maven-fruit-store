package com.store.entity;

public class RecipeFruit {
	
	private Integer id;
	private double gPer100ml;
	
	public RecipeFruit() {}
	
	public RecipeFruit(Integer id, double gPer100ml) {
		this.id = id;
		this.gPer100ml = gPer100ml;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getgPer100ml() {
		return gPer100ml;
	}

	public void setgPer100ml(double gPer100ml) {
		this.gPer100ml = gPer100ml;
	}
	
}
