package lt.baltic.talents.project.cafeteria;

public class Food extends Dishes {

    private double weight;

    public Food(String name, double price, DishType dishType, int qty, double weight){
        super(name, price, dishType, qty);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Food: " + getName() +
                ", " + super.getQty() +
                ", pcs, price: " + super.getPrice() +
                " EUR, weight: " + (weight * super.getQty()) + " g";
    }
}
