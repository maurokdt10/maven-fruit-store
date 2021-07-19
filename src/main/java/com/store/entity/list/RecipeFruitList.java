package com.store.entity.list;

import java.util.List;

import com.store.entity.RecipeFruit;

public class RecipeFruitList {

	private List<RecipeFruit> listRecipeFruit;

	public RecipeFruitList() {}

	public RecipeFruitList(List<RecipeFruit> listRecipeFruit) {
		this.listRecipeFruit = listRecipeFruit;
	}

	public List<RecipeFruit> getListRecipeFruit() {
		return listRecipeFruit;
	}

	public void setListRecipeFruit(List<RecipeFruit> listRecipeFruit) {
		this.listRecipeFruit = listRecipeFruit;
	}
	
}
