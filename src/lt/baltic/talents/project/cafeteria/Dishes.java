package lt.baltic.talents.project.cafeteria;

/**
 * An abstract class for Dishes
 * @author Saulius Jackunas
 *
 */

public abstract class Dishes {

    private String name;
    private double price;
    private int qty;
    private DishType dishType;

    public Dishes(String name, double price, DishType dishType, int qty) {
        this.name = name;
        this.price = price;
        this.dishType = dishType;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public DishType getDishType() {
        return dishType;
    }

    public double calculateDishSum() {
        return price * qty;
    }
}
