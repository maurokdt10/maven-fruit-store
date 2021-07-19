package com.store;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.store.entity.Ingredient;
import com.store.service.IStoreService;
import com.store.util.CommandLineTable;

@SpringBootApplication
public class FruitDrinkStoreApplication implements CommandLineRunner{
	
	@Autowired
	private IStoreService ingredientService;
	
	public static void main(String[] args) {
		SpringApplication.run(FruitDrinkStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fruitStoreMainRun();
	}

	private void fruitStoreMainRun() throws Exception {
		Scanner scan = null;
		String userName;
		Boolean mainControl = true;
		int optionMenu = 0;
		try {
			scan = new Scanner(System.in);
			System.out.println("Please enter your name: ");
			userName = scan.next();
			System.out.println("**********************************");
			System.out.println("******** Fruit Drink Store *******");
			System.out.println("**********************************");
			System.out.println("*********** Git Test N-1 *********");
			System.out.println("*********** Git Test N-4 *********");
			System.out.println("*********** Git Test N-6 vFinal *********");
			System.out.println("Made by Mauricio Parreno.");
			System.out.println("Welcome "+userName);
			this.ingredientService.beginInventory(userName);
			while (mainControl) {
				System.out.println("-------------------- MAIN MENU --------------------");
				System.out.println("Choose an option menu: ");
				System.out.println("1. Begin inventary");
				System.out.println("2. Get current Inventary");
				System.out.println("3. Sell a fruit drink");
				System.out.println("4. Provide a report of daily sales");
				System.out.println("5. Leave system");
				System.out.println();
				System.out.println("Enter your option: ");
				try {
					scan = new Scanner(System.in);
					optionMenu = Integer.parseInt(scan.next());
					switch (optionMenu) {
						case 1:
							beginInventary(scan);
							break;
						case 2:
							printInventoryList();
							break;
						case 3:
							sellFruitDrink(scan);
							break;
						case 4:
							this.ingredientService.printGeneralSalesList();
							break;
						case 5:
							mainControl = false;
							break;
						default:
							System.out.println("option "+optionMenu+" not valid value. Try again");
							break;
					}
	            } catch (NumberFormatException nfe) {
	                System.out.println("Not valid value: "+nfe);
	                System.out.println("Try again");
	            }
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			System.out.println("--------------------------------------------------");
			System.out.println("Leaving system. Have a good day!");
			userName = null;
			mainControl = null;
			scan.close();
		}
	}

	private void sellFruitDrink(Scanner scanFruitSell) throws Exception {
		Boolean mainControl = true;
		int optionMenuSell = 0;
		String optionsMenuSell = "";
		try {
			optionsMenuSell = this.ingredientService.getSellMenu();
			while (mainControl) {
				System.out.println("---------------- Sell Fruit Drink ------------------");
				System.out.println("Chosse an ingrediente to prepare and sell: ");
				System.out.println(optionsMenuSell+"0. Back to the main menu");
				System.out.println();
				System.out.println("Enter your option: ");
				try {
					scanFruitSell = new Scanner(System.in);
					optionMenuSell = Integer.parseInt(scanFruitSell.next());
					if (this.ingredientService.checkMenuSellOption(optionMenuSell)) {
						//choose size cup
						//get fruit drink name
						cupSizeMenu(scanFruitSell,optionMenuSell,this.ingredientService.getFruitNameByIngredientId(optionMenuSell));
					}else if (optionMenuSell == 0) {
						mainControl = false;
					}else {
						System.out.println("option "+optionMenuSell+" Not valid value. Try again");
					}
	            } catch (NumberFormatException nfe) {
	                System.out.println("Not valid value: "+nfe);
	                System.out.println("Try again");
	            }
			}
			System.out.println("--------------------------------------------------");
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			mainControl = null;
			optionsMenuSell = null;
		}
	}

	private void cupSizeMenu(Scanner scanCupSize,Integer optionMenuSell,String fruitName) throws Exception {
		Boolean mainControl = true;
		int optionMenuCup = 0;
		String optionsMenuCupSize = "";
		try {
			optionsMenuCupSize = this.ingredientService.getCupSizeMenu();
			while (mainControl) {
				System.out.println("---------------- Cup Size Drink "+fruitName+" ------------------");
				System.out.println("Chosse an size cup: ");
				System.out.println(optionsMenuCupSize+"0. Back to the sell fruit drink menu");
				System.out.println();
				System.out.println("Enter your option: ");
				try {
					scanCupSize = new Scanner(System.in);
					optionMenuCup = Integer.parseInt(scanCupSize.next());
					if (this.ingredientService.checkMenuCupOption(optionMenuCup)) {
						//choose size cup
						this.ingredientService.sellDrink(optionMenuSell,optionMenuCup);
					}else if (optionMenuCup == 0) {
						mainControl = false;
					}else {
						System.out.println("option "+optionMenuCup+" Not valid value. Try again");
					}
	            } catch (NumberFormatException nfe) {
	                System.out.println("Not valid value: "+nfe);
	                System.out.println("Try again");
	            }
			}
			System.out.println("--------------------------------------------------");
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			mainControl = null;
			optionsMenuCupSize = null;
		}
	}

	private void printInventoryList() {
		List<Ingredient> myInventory = this.ingredientService.getInventory();
		CommandLineTable st = new CommandLineTable();
        st.setRightAlign(true);
        st.setShowVerticalLines(true);
        st.setHeaders("PRODUCT ID.", "PRODUCT NAME", "PRODUCT QUANTITY");
        for (Ingredient ingredient : myInventory) {
        	st.addRow(String.valueOf(ingredient.getId()), ingredient.getName(), ingredient.getQuantity()+"(g)");
        }
        System.out.println("----- CURRENT INGREDIENTS INVENTORY -----");
        st.print();
	}

	private void beginInventary(Scanner scanInventary) throws Exception {
		Boolean mainControl = true;
		int optionMenu = 0;
		String optionsMenuInventary = "";
		try {
			optionsMenuInventary = this.ingredientService.getInventoryMenu();
			while (mainControl) {
				System.out.println("---------------- Inventary Menu ------------------");
				System.out.println("Chosse an ingrediente to add storage: ");
				System.out.println(optionsMenuInventary+"0. Back to the main menu");
				System.out.println();
				System.out.println("Enter your option: ");
				try {
					scanInventary = new Scanner(System.in);
					optionMenu = Integer.parseInt(scanInventary.next());
					if (this.ingredientService.checkMenuInventaryOption(optionMenu)) {
						currentIngredientMenu(optionMenu,scanInventary);
					}else if (optionMenu == 0) {
						mainControl = false;
					}else {
						System.out.println("option "+optionMenu+" Not valid value. Try again");
					}
	            } catch (NumberFormatException nfe) {
	                System.out.println("Not valid value: "+nfe);
	                System.out.println("Try again");
	            }
			}
			System.out.println("--------------------------------------------------");
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			mainControl = null;
			optionsMenuInventary = null;
		}
	}

	private void currentIngredientMenu(Integer id,Scanner scanIngredient) {
		Ingredient currentIngredient = this.ingredientService.getIngredientById(id);
		printCurrentIngredientInfo(currentIngredient,"BEFORE");
		System.out.println("Enter value to add storage of "+currentIngredient.getName()+"(g): ");
		try {
			scanIngredient = new Scanner(System.in);
			double newValue = Double.parseDouble(scanIngredient.next());
			newValue+=currentIngredient.getQuantity();
			this.ingredientService.updateIngredient(id, newValue);
			currentIngredient = this.ingredientService.getIngredientById(id);
			printCurrentIngredientInfo(currentIngredient,"AFTER");
        } catch (NumberFormatException nfe) {
            System.out.println("Not valid value: "+nfe);
            System.out.println("Try again");
        } finally {
        	currentIngredient = null;
		}
	}

	private void printCurrentIngredientInfo(Ingredient currentIngredient,String title) {
		CommandLineTable st = new CommandLineTable();
        st.setRightAlign(true);
        st.setShowVerticalLines(true);
        st.setHeaders("PRODUCT ID.", "PRODUCT NAME", "PRODUCT QUANTITY");
    	st.addRow(String.valueOf(currentIngredient.getId()), currentIngredient.getName(), currentIngredient.getQuantity()+"(g)");
        System.out.println("----- CURRENT INVENTORY "+title+" UPDATE -----");
        st.print();
	}

}
