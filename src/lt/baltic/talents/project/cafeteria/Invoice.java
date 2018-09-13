package lt.baltic.talents.project.cafeteria;

import java.time.LocalDateTime;
import java.util.Random;

public class Invoice extends Order {

    private int tableNo;
    private LocalDateTime endingDate;
    private Employee waiter;

    public Invoice (int tableNo, Order order) {
        super(order);
        this.tableNo = tableNo;
        this.endingDate = LocalDateTime.now();
        this.waiter = assignWaiter();
    }

    @Override
    public String toString() {
        return "Invoice:\n" +
                "Table no.: " + tableNo + "\n" +
                "Order placed at: " + super.getStartingTime().format(Utils.TIME_FORMATTER) + "\n" +
                "Ordered dishes: " + super.toString() + "\n" +
                "Total amount including VAT: " + super.getSum() + " EUR\n" +
                "Your waiter was: " + waiter.getName() + "\n" +
                "Date and time of the invoice: " + endingDate.format(Utils.TIME_FORMATTER) + "\n";
    }

    public int getTableNo() {
        return tableNo;
    }

    public LocalDateTime getEndingTime() {
        return endingDate;
    }

    public Employee getWaiter() {
        return waiter;
    }

    public Employee assignWaiter() {
        Random random = new Random();
        int id = random.nextInt(2) + 1;
        switch (id) {
            case 1:
                return Employee.JURIJUS;
            case 2:
                return Employee.AISTE;
            case 3:
                return Employee.GIEDRIUS;
            default:
                break;
        }
        return null;
    }

}
