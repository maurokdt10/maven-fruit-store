package com.store.service;

import java.util.List;

import com.store.entity.Ingredient;

public interface IStoreService {
	
	void beginInventory(String userName) throws Exception;
	
	void updateIngredient(Integer id, double newValue);
	
	void sellDrink(Integer idIngredientSelected,Integer idCupSizeSelected) throws Exception;
	
	void printGeneralSalesList();
	
	boolean checkMenuInventaryOption(Integer idMenu);
	
	boolean checkMenuSellOption(Integer idMenu);
	
	boolean checkMenuCupOption(Integer idMenu);
	
	List<Ingredient> getInventory();
	
	Ingredient getIngredientById(Integer id);
	
	String getInventoryMenu();
	
	String getSellMenu();
	
	String getCupSizeMenu();

	String getFruitNameByIngredientId(Integer id);
}
