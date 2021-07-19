package com.store.entity;

public class RecipeOther {
	
	private Integer id;
	private double density;
	private double recipePortion;
	private String recipePortionUnit;
	
	public RecipeOther() {}

	public RecipeOther(Integer id, double density, double recipePortion, String recipePortionUnit) {
		this.id = id;
		this.density = density;
		this.recipePortion = recipePortion;
		this.recipePortionUnit = recipePortionUnit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public double getRecipePortion() {
		return recipePortion;
	}

	public void setRecipePortion(double recipePortion) {
		this.recipePortion = recipePortion;
	}

	public String getRecipePortionUnit() {
		return recipePortionUnit;
	}

	public void setRecipePortionUnit(String recipePortionUnit) {
		this.recipePortionUnit = recipePortionUnit;
	}

}
