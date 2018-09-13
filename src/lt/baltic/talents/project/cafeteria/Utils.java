package lt.baltic.talents.project.cafeteria;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * This is a helper class for Cafeteria
 *
 * @author Saulius Jackunas
 */

public class Utils {

    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    //A formatter for time and date
    public static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    //Getting table number from user input and checking if it fits the range
    public static int getTableNo(Scanner input) {

        int tableNo = 0;
        do {
            System.out.println("Please enter your table number: ");
            try {
                tableNo = input.nextInt();
                if (Utils.checkInterval(0, Cafeteria.TABLES, tableNo)) {
                    break;
                } else {
                    logger.info("There is no such table. Please try again.");
                }
            } catch (Exception e) {
                logger.info("You have not entered a valid number. Try again.");
                input.skip(".*");
            }
        } while (true);
        return tableNo;
    }

    //Checking if given number is within interval a..b (inclusive)
    public static boolean checkInterval(int a, int b, int number) {
        return number >= a && number <= b ? true : false;
    }
}
