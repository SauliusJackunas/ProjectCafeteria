package lt.baltic.talents.project.cafeteria;

public class Drink extends Dishes {

    private double vol;

    public Drink(String name, double price, DishType dishType, int qty, double vol) {
        super(name, price, dishType, qty);
        this.vol = vol;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

    @Override
    public String toString() {
        return "Drink = " + getName() +
                ", price = " + super.getPrice() +
                " EUR, volume = " + vol +
                " ml, quantity = " + super.getQty() + " pcs.";
    }
}
