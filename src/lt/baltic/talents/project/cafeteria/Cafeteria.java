package lt.baltic.talents.project.cafeteria;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Cafeteria implements Bills {

	private static final Logger logger = Logger.getLogger(Cafeteria.class.getName());

	private String name;
	private List<Dishes> menu;
	private Map<Integer, Order> allOrders;

	public static final double TIPS = 0.1d;
	public static final int TABLES = 5;

	public Cafeteria(String name) {
		this.name = name;
		allOrders = new HashMap<>();
		setMenu("en-us");
	}

	public String getName() {
		return name;
	}

	public List<Dishes> getMenu() {
		return menu;
	}

	public void setMenu(List<Dishes> menu) {
		this.menu = menu;
	}

	public void printMenu() {
		int i = 1;
		for (Dishes dish : menu) {
			System.out.println(i + " " + dish);
			i++;
		}
	}

	public double getOrderSum(int tableNo) {
		return allOrders.get(tableNo).getSum();
	}

	public String printOrder(Integer tableNo) {
		return allOrders.get(tableNo).toString();
	}

	public void printAllOrders() {
		System.out.println("The following tables are occupied:");
		for (Integer index : allOrders.keySet()) {
			System.out.println(index + " ");
		}
		System.out.println();
	}

	public void setMenu(String locale) {
		Locale.setDefault(new Locale(locale));
		ResourceBundle resourceBundle = ResourceBundle.getBundle("cafeteria");

		/**
		 * Creating the menu list
		 */
		menu = new ArrayList<>();
		menu.add(new Drink(resourceBundle.getString("cola"), 2.00, DishType.BEVERAGES, 0, 0.5));
		menu.add(new Drink(resourceBundle.getString("sprite"), 2.00, DishType.BEVERAGES, 0, 0.5));
		menu.add(new Drink(resourceBundle.getString("beer"), 1.00, DishType.ALCOHOL, 0, 0.5));
		menu.add(new Drink(resourceBundle.getString("cider"), 42.00, DishType.ALCOHOL, 0, 0.5));
		menu.add(new Food(resourceBundle.getString("ribs"), 24.00, DishType.MAIN, 0, 500));
		menu.add(new Drink(resourceBundle.getString("coffee"), 1.60, DishType.HOT_DRINKS, 0, 0.2));
		menu.add(new Drink(resourceBundle.getString("tea"), 0.60, DishType.HOT_DRINKS, 0, 0.4));
		menu.add(new Food(resourceBundle.getString("pork"), 29.00, DishType.MAIN, 0, 500));
		menu.add(new Food(resourceBundle.getString("tabaca"), 28.00, DishType.MAIN, 0, 1000));
		menu.add(new Drink(resourceBundle.getString("wine"), 2.60, DishType.ALCOHOL, 0, 0.2));
		
		Collections.sort(menu, new DishSorting());
	}

	public void selectMenuLanguage(Scanner in) {
		int language = 0;

		do {
			System.out
					.println("Please select your preferred language: 1 for menu in English, 2 for menu in Lithuanian.");
			try {
				language = in.nextInt();
				switch (language) {
				case 1:
					setMenu("en-us");
					break;
				case 2:
					setMenu("lt-lt");
					break;
				default:
					logger.info("This language is not supported. Select another one.");
					break;
				}
			} catch (InputMismatchException e) {
				logger.info("Please enter a number for menu.");
				in.skip(".*");
			}
		} while (language <= 1 && language >= 2);
	}

	@Override
	public Invoice checkInvoice(Integer tableNo, LocalDateTime dateTime) {
		return null;
	}

	public double tips(int tableNo) {
		return Math.round(getOrderSum(tableNo) * Cafeteria.TIPS);
	}

	public void printInvoicesByQuarterFromFile() {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("resources/cafeteria.json")))) {
			String line;
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Dishes.class, new AdapterForElements());
			Gson gson = gsonBuilder.create();

			System.out.println("Invoiced amounts by quarters: ");
			Invoice invoice;
			double sum = 0.0;
			int currentYear = 0;
			int currentQuarter = 0;
			while ((line = bufferedReader.readLine()) != null) {
				invoice = gson.fromJson(line, Invoice.class);
				if (currentYear == 0 && currentQuarter == 0) {
					currentYear = invoice.getEndingTime().getYear();
					currentQuarter = invoice.getEndingTime().get(IsoFields.QUARTER_OF_YEAR);
				}
				if ((invoice.getEndingTime().getYear() == currentYear)
						&& (invoice.getEndingTime().get(IsoFields.QUARTER_OF_YEAR) == currentQuarter)) {
					sum += invoice.getSum();
				} else {
					System.out.println(currentYear + " quarter " + currentQuarter + ", total amount: " + sum + " EUR");
					currentQuarter = invoice.getEndingTime().get(IsoFields.QUARTER_OF_YEAR);
					currentYear = invoice.getEndingTime().getYear();
					sum = invoice.getSum();
				}
			}
			System.out.println(currentYear + " quarter " + currentQuarter + ", total amount: " + sum + " EUR");
		} catch (FileNotFoundException e) {
			logger.warning(e.getMessage());
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
	}

	public void printAllInvoicesFromFile() {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("resources/cafeteria.json")))) {
			String line;
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Dishes.class, new AdapterForElements());
			Gson gson = gsonBuilder.create();
			System.out.println("List of the invoices: ");
			int i = 1;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.print("Invoice No.: " + i + " ");
				System.out.println(gson.fromJson(line, Invoice.class).toString());
				i++;
			}
		} catch (FileNotFoundException e) {
			logger.warning(e.getMessage());
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
	}

	public void getReports(Scanner input) {

		int menu = 0;
		do {
			System.out.println(
					"Please select from the following options: 1 - print all possible invoices, 2 - amounts per quarters, 0 - back");
			try {
				menu = input.nextInt();
				switch (menu) {
				case 1: // prints all possible invoices
					printAllInvoicesFromFile();
					break;
				case 2: // prints all amount by quarters
					printInvoicesByQuarterFromFile();
					break;

				case 0: // exit
					break;

				default: // repeat your selection
					logger.info("Not a valid number selected. Try again");
					break;
				}
			} catch (InputMismatchException e) {
				logger.info("It is not a number of menu. Try again");
				input.skip(".*");
			}
		} while (menu != 0);

	}
	
	public Order getOrder(Integer tableNo) {
		return allOrders.get(tableNo);
	}
	
	public Dishes getMenuItem(int pos) {
		return menu.get(pos);
	}
	
	public void setOrder(Integer tableNumber, Scanner input) {
		int dishNo = 0;
		int dishQty = 0;
		Order order = null;
		
		do {
			try {
				System.out.println("Enter desired dish number or '0' to exit: ");
				dishNo = input.nextInt();
				if (dishNo == 0) {
					break;
				}
				if (!Utils.checkInterval(0, getMenu().size(), dishNo)) {
					System.out.println("We do not have such dish. Please try again.");
					continue;
				}
				System.out.println("How many " + menu.get(dishNo).getName() + " you want to order or '0' to exit: ");
				dishQty = input.nextInt();
				if (dishQty == 0) {
					break;
				}
				if (dishQty > 0) {
					if (order == null) {
						order = new Order(getMenuItem(dishNo), dishQty);
					} else {
						order.addDish(getMenuItem(dishNo), dishQty);
					}
				} else {
					logger.info("We do not have such dish. Please try again.");
				}
			} catch (InputMismatchException e) {
				logger.info("You have not entered a valid number. Try again.");
				input.skip(".*");
			}
		} while (true);
		
		allOrders.put(tableNumber, order);
	}
	
	public void ordersWithTotalAmount() {
		System.out.println("Report of the tables: ");
		Iterator<Integer> keys = allOrders.keySet().iterator();
		Integer nextTable = 0;

		if (keys.hasNext()) {
			nextTable = keys.next();
		}

		for (int i = 1; i <= TABLES; i++) {
			if (!allOrders.isEmpty()) {
				if (i != nextTable) {
					System.out.println(i + " table is empty");
				} else {
					System.out.println("The table No.: " + i + " has ordered the following: ");
					System.out.println(
							"This order was started at: " + getOrder(i).getStartingTime().format(Utils.TIME_FORMATTER));
					System.out.print(printOrder(i));
					System.out.println("Grand total: " + getOrderSum(i) + " EUR");
					if (keys.hasNext()) {
						nextTable = keys.next();
					}
				}
			} else {
				System.out.println("The table " + i + " is still empty.");
			}
		}
		System.out.println();
	}

	@Override
	public String issueAnInvoice(Integer tableNo) {
		Invoice invoice = new Invoice(tableNo, allOrders.get(tableNo));
		saveInvoice(invoice);
		allOrders.remove(tableNo);
		return invoice.toString();
	}

	@Override
	public void saveInvoice(Invoice invoice) {

		try (BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(new File("resources/cafeteria.json"), true))) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Dishes.class, new AdapterForElements());
			Gson gson = gsonBuilder.create();
			String gsonInvoice = gson.toJson(invoice);
			bufferedWriter.write(gsonInvoice + "\n");
			bufferedWriter.flush();

		} catch (FileNotFoundException e) {
			logger.warning(e.getMessage());
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
	}
}
