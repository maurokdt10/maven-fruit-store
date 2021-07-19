package com.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.entity.Ingredient;
import com.store.repository.IStoreRepo;

@Service
public class StoreServiceImpl implements IStoreService{

	@Autowired
	private IStoreRepo ingredientRepo;

	@Override
	public void beginInventory(String userName) throws Exception {
		this.ingredientRepo.beginInventory(userName);
	}

	@Override
	public List<Ingredient> getInventory() {
		return this.ingredientRepo.getInventory();
	}

	@Override
	public Ingredient getIngredientById(Integer id) {
		return this.ingredientRepo.getIngredientById(id);
	}

	@Override
	public String getInventoryMenu() {
		return this.ingredientRepo.getInventoryMenu();
	}

	@Override
	public String getSellMenu() {
		return this.ingredientRepo.getSellMenu();
	}

	@Override
	public boolean checkMenuInventaryOption(Integer id) {
		return this.ingredientRepo.checkMenuInventaryOption(id);
	}

	@Override
	public boolean checkMenuSellOption(Integer idMenu) {
		return this.ingredientRepo.checkMenuSellOption(idMenu);
	}

	@Override
	public void updateIngredient(Integer id, double newValue) {
		this.ingredientRepo.updateIngredient(id, newValue);
	}

	@Override
	public void sellDrink(Integer idIngredientSelected, Integer idCupSizeSelected) throws Exception {
		this.ingredientRepo.sellDrink(idIngredientSelected, idCupSizeSelected);
	}

	@Override
	public String getCupSizeMenu() {
		return this.ingredientRepo.getCupSizeMenu();
	}

	@Override
	public boolean checkMenuCupOption(Integer idMenu) {
		return this.ingredientRepo.checkMenuCupOption(idMenu);
	}

	@Override
	public String getFruitNameByIngredientId(Integer id) {
		return this.ingredientRepo.getFruitNameByIngredientId(id);
	}

	@Override
	public void printGeneralSalesList() {
		this.ingredientRepo.printGeneralSalesList();
	}

}
