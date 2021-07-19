package com.store.entity.list;

import java.util.List;

import com.store.entity.RecipeOther;

public class RecipeOtherList {

	private List<RecipeOther> listRecipeOther;

	public RecipeOtherList() {}
	
	public RecipeOtherList(List<RecipeOther> listRecipeOther) {
		this.listRecipeOther = listRecipeOther;
	}

	public List<RecipeOther> getListRecipeOther() {
		return listRecipeOther;
	}

	public void setListRecipeOther(List<RecipeOther> listRecipeOther) {
		this.listRecipeOther = listRecipeOther;
	}
	
}
