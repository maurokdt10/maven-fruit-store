package com.store.entity.list;

import java.util.List;

import com.store.entity.Ingredient;

public class IngredientList {
	
	private List<Ingredient> listIngredients;

	public IngredientList(List<Ingredient> listIngredients) {
		this.listIngredients = listIngredients;
	}
	
	public IngredientList() {
		
	}
	
	public List<Ingredient> getListIngredients() {
		return listIngredients;
	}
	
	public void setListIngredients(List<Ingredient> listIngredients) {
		this.listIngredients = listIngredients;
	}
	@Override
	public String toString() {
		return "Ingredients [listIngredients=" + listIngredients + "]";
	}
	
}
