package com.store.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.store.entity.Cup;
import com.store.entity.Ingredient;
import com.store.entity.RecipeFruit;
import com.store.entity.RecipeOther;
import com.store.entity.list.CupList;
import com.store.entity.list.IngredientList;
import com.store.entity.list.RecipeFruitList;
import com.store.entity.list.RecipeOtherList;
import com.store.model.SalesDetail;
import com.store.util.CommandLineTable;

@Repository
public class StoreRepoImpl implements IStoreRepo{
	
	public static final String ML_UNIT = "ml";
	
	private String userName;
	
	private Integer indexMainIdCount;
	
	private List<Ingredient> inventaryList;
	
	private List<RecipeFruit> recipeFruitList;
	
	private List<RecipeOther> recipeOtherList;
	
	private List<SalesDetail> salesDetailList;
	
	private List<Cup> cupList;
	
	@SuppressWarnings("resource")
	@Override
	public void beginInventory(String userName) throws Exception {
		this.userName = userName;
		indexMainIdCount = 0;
		ApplicationContext appContext = null;
		IngredientList listIngredients;
		RecipeFruitList listRecipeFruits;
		RecipeOtherList listRecipeOthers;
		CupList listCups;
		try {
			inventaryList = new ArrayList<Ingredient>();
			salesDetailList = new ArrayList<SalesDetail>();
			
			appContext = new ClassPathXmlApplicationContext("com/store/xml/beans.xml");
			
			listCups = (CupList)appContext.getBean("cupsListBean");
			cupList = listCups.getListCups();
			
			listIngredients = (IngredientList) appContext.getBean("ingredientListBean");
			inventaryList = listIngredients.getListIngredients();
			
			listRecipeFruits = (RecipeFruitList) appContext.getBean("recipieFruitListBean");
			recipeFruitList = listRecipeFruits.getListRecipeFruit();
			
			listRecipeOthers = (RecipeOtherList) appContext.getBean("recipieOtherListBean");
			recipeOtherList = listRecipeOthers.getListRecipeOther();
			
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			listIngredients = null;
			listRecipeFruits = null;
			listRecipeOthers = null;
			listCups = null;
			((ConfigurableApplicationContext) appContext).close();
		}
		
	}

	@Override
	public List<Ingredient> getInventory() {
		return inventaryList;
	}

	private Boolean checkStockItem(double item1,double item2) {
		if ((item1-item2)<0) {
			return true;//negative
		}else {
			return false;//positive
		}
	}
	
	@Override
	public void sellDrink(Integer idIngredientSelected,Integer idCupSizeSelected) throws Exception {
		Boolean mainControlFruit = true;
		Boolean mainControlOthers = true;
		
		String errorLog ="Insufficient resources\n";
		
		Ingredient currentIngredient = getIngredientById(idIngredientSelected);
		
		RecipeFruit currentFruitRecipe = getRecipeFruitById(idIngredientSelected);
		
		double currentFruitGramsPer100ml = (currentFruitRecipe.getgPer100ml()*50)/100;
		
		Cup currentCup = getCupById(idCupSizeSelected);
		
		double cupMlPrefix = (currentCup.getMlSize()/100);
		
		double finalFruitGramsPer100ml = cupMlPrefix*currentFruitGramsPer100ml;

		if (checkStockItem(currentIngredient.getQuantity(), finalFruitGramsPer100ml)) {
			errorLog+="- Available "+currentIngredient.getName() +" (g): "+doubleDecimalRound(currentIngredient.getQuantity())+"\n"+
					  "- Necessary "+currentIngredient.getName()+" (g): "+doubleDecimalRound(finalFruitGramsPer100ml)+"\n";
			mainControlFruit = false;
		}
		for (RecipeOther recipeOther : recipeOtherList) {
			//check if is ml or grams
			double otherIngredientFactor = 0;
			if (recipeOther.getRecipePortionUnit().equalsIgnoreCase(ML_UNIT)) {
				//transform to grams
				otherIngredientFactor = (recipeOther.getRecipePortion()*recipeOther.getDensity())*cupMlPrefix;
			}else {
				otherIngredientFactor = (recipeOther.getRecipePortion())*cupMlPrefix;
			}
			//check if there is enough current ingredient
			Ingredient currentOtherIngredient = getIngredientById(recipeOther.getId());
			if (checkStockItem(currentOtherIngredient.getQuantity(), otherIngredientFactor)) {
				errorLog+="- Available "+currentOtherIngredient.getName() +" (g): "+doubleDecimalRound(currentOtherIngredient.getQuantity())+"\n"+
						  "- Necessary "+currentOtherIngredient.getName()+" (g): "+doubleDecimalRound(otherIngredientFactor)+"\n";
				mainControlOthers = false;
//				break;
			}
		}
		if (mainControlFruit && mainControlOthers) {
			/**
			 * subtract inventory fruit
			 */
			updateIngredient(idIngredientSelected, currentIngredient.getQuantity()-finalFruitGramsPer100ml);
			/**
			 * subtract inventory others
			 */
			
			for (RecipeOther recipeOtherFinal : recipeOtherList) {
				//check if is ml or grams
				double otherIngredientFactor = 0;
				if (recipeOtherFinal.getRecipePortionUnit().equalsIgnoreCase(ML_UNIT)) {
					//transform to grams
					otherIngredientFactor = (recipeOtherFinal.getRecipePortion()*recipeOtherFinal.getDensity())*cupMlPrefix;
				}else {
					otherIngredientFactor = (recipeOtherFinal.getRecipePortion())*cupMlPrefix;
				}
				//update values
				Ingredient currentOtherIngredient = getIngredientById(recipeOtherFinal.getId());
				updateIngredient(recipeOtherFinal.getId(), currentOtherIngredient.getQuantity()-otherIngredientFactor);
				
			}
			
			System.out.println("The sale has been generated. Inventory has been updated.");
			/**
			 * print ticket sale.
			 */
			indexMainIdCount++;
			SalesDetail sale = new SalesDetail(
					String.valueOf(indexMainIdCount), 
					getCurrentHourSale(false), 
					currentIngredient.getName()+" fruit drink - "+currentCup.getMlSize()+" ml", 
					currentCup.getPrice());
			System.out.println("********************************");
			System.out.println("********* TICKET SALE **********");
			System.out.println("Seller: "+userName);
			System.out.println("Date : "+getCurrentHourSale(true));
			printSaleList(Stream.of(sale).collect(Collectors.toList()));
			/**
			 * save sale in a list.
			 */
			salesDetailList.add(sale);
		}else {
			System.out.println("WARNING: Can't generate sale");
			System.out.println(errorLog);
		}
		
	}

	@Override
	public Ingredient getIngredientById(Integer id) {
		return inventaryList.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);
	}

	@Override
	public void updateIngredient(Integer id, double newValue) {
		Ingredient currentIngredient = getIngredientById(id);
		int index =  inventaryList.indexOf(currentIngredient);
		currentIngredient.setQuantity(doubleDecimalRound(newValue));
		inventaryList.set(index, currentIngredient);
	}

	@Override
	public String getInventoryMenu() {
		return inventaryList.stream()
		.map(i -> i.getId()+". "+i.getName()+"\n")
		.collect(Collectors.joining());
	}

	@Override
	public String getSellMenu() {
		return inventaryList.stream()
				.filter(f -> Boolean.parseBoolean(f.getIsFruit()))
				.map(i -> i.getId()+". "+i.getName()+"\n")
				.collect(Collectors.joining());
	}

	@Override
	public boolean checkMenuInventaryOption(Integer idMenu) {
		return inventaryList.stream()
				.anyMatch(i -> i.getId() == idMenu);
	}

	@Override
	public boolean checkMenuSellOption(Integer idMenu) {
		return inventaryList.stream()
				.filter(f -> Boolean.parseBoolean(f.getIsFruit()))
				.anyMatch(i -> i.getId() == idMenu);
	}

	public RecipeFruit getRecipeFruitById(Integer id) {
		return recipeFruitList.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);
	}
	
	public Cup getCupById(Integer id) {
		return cupList.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
	}

	@Override
	public String getCupSizeMenu() {
		return cupList.stream()
				.map(c -> c.getId()+". "+c.getName()+" -> ("+c.getMlSize()+" ml)"+"\n")
				.collect(Collectors.joining());
	}

	@Override
	public boolean checkMenuCupOption(Integer idMenu) {
		return cupList.stream()
				.anyMatch(c -> c.getId() == idMenu);
	}
	
	
	private double doubleDecimalRound(double number ) {
		return Math.round(number * 100.0) / 100.0;
	}
	
	private String getCurrentHourSale(Boolean option) {
		if (option) {
			LocalDateTime today=LocalDateTime.now();
			DateTimeFormatter formatToday=DateTimeFormatter.ofPattern("MM/dd/yy HH:mm:ss");
			return today.format(formatToday);
		}else {
			LocalTime timeNow=LocalTime.now();
			DateTimeFormatter formatTimeNow=DateTimeFormatter.ofPattern("HH:mm");
			return timeNow.format(formatTimeNow);
		}
	}
	
	private void printSaleList(List<SalesDetail> details) {
		CommandLineTable st = new CommandLineTable();
		st.setShowVerticalLines(true);
		if (details.isEmpty()) {
	        st.setHeaders("WARNING");
	        st.addRow("YOU DON'T HAVE ANY SALES YET");
		}else {
			double total = 0;
	        st.setRightAlign(true);
	        st.setHeaders("ID. SALE", "HOUR", "DETAIL SALE","TOTAL");
	        for (SalesDetail sale : details) {
	        	st.addRow(sale.getId(), sale.getHour(), sale.getDetail(),String.valueOf(sale.getTotal()));
	        	total+=sale.getTotal();
			}
	        st.addRow("", "", "","");
	        st.addRow("", "", "TOTAL",  String.valueOf(doubleDecimalRound(total)));
		}
		st.print();
	}
	
	@Override
	public void printGeneralSalesList() {
		System.out.println("******************************************");
		System.out.println("********* REPORT OF DAILY SALES **********");
		System.out.println("Seller: "+userName);
		System.out.println("Date : "+getCurrentHourSale(true));
		printSaleList(salesDetailList);
	}

	@Override
	public String getFruitNameByIngredientId(Integer id) {
		return inventaryList.stream()
				.filter(f -> f.getId() == id)
				.map(v->v.getName())
				.collect(Collectors.joining());
	}
}
