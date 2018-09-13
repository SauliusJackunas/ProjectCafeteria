package lt.baltic.talents.project.cafeteria;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Project cafeteria. For customer service
 * 
 * @author Saulius Jackunas
 */

public class Main {

	private static final Logger logger = Logger.getLogger(Main.class.getName());

	// Running the logger file
	static {
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream(new File("resources/logging.properties")));
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SecurityException, IOException {

		/**
		 * Initialization. Creating a new cafeteria with a powerful name and making a
		 * new menu.
		 */

		Cafeteria cafeteria = new Cafeteria("SJDrive");

		System.out.println(
				"Welcome to \"" + cafeteria.getName() + "\". We have " + Cafeteria.TABLES + " tables available.");

		/**
		 * Initializing variables helpers.
		 */

		String status;
		int tableNo;

		Scanner in = new Scanner(System.in);

		/**
		 * Starting the main program
		 */
		do {
			System.out.println(
					"Select your option: New (O)rder, (A)ppend existing order, (C)heck all orders, issue an (I)nvoice, choose preferred (L)anguage, (R)eport or (Q)uit the program");
			System.out.println("Current language is: " + Locale.getDefault().getLanguage());
			status = in.next();

			switch (status.toLowerCase()) {
			// Making a new order or filling in the existing
			case "a":
				cafeteria.printAllOrders();
			case "o":
				// Getting the table No.
				tableNo = Utils.getTableNo(in);

				// Handing out the MENU and filling up the order
				System.out.println("Please take a look at our menu and make an order of your desire:");
				cafeteria.printMenu();
				cafeteria.setOrder(tableNo, in);

				// Printing out the order
				System.out.println("Your ordered meal: ");
				// Checking if there is an existing order
				if (cafeteria.getOrder(tableNo) != null) {
					System.out.println(cafeteria.printOrder(tableNo));
				}
				break;

			// Displaying active orders
			case "c":
				cafeteria.ordersWithTotalAmount();
				break;
			// Printing out the active tables and handing out the invoice
			case "i":
				cafeteria.printAllOrders();
				tableNo = Utils.getTableNo(in);
				if (cafeteria.getOrder(tableNo) != null) {
					double tips = cafeteria.tips(tableNo);
					System.out.print(cafeteria.issueAnInvoice(tableNo) + "Recommended tips: " + tips + "\n");
				} else {
					logger.info("Table No.: " + tableNo + " is not occupied.");
				}
				break;
			// Quits the menu
			case "q":
				break;

			// Selecting the preferred menu language
			case "l":
				cafeteria.selectMenuLanguage(in);
				break;

			// Reports menu
			case "r":
				cafeteria.getReports(in);
				break;

			// If there is no such menu option
			default:
				logger.info("There is no such option. Try again.");
				break;
			}
		} while (status.compareToIgnoreCase("q") != 0);

		// Closing the scanner
		in.close();

	}
}