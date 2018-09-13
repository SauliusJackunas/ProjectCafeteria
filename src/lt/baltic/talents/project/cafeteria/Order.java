package lt.baltic.talents.project.cafeteria;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<Dishes> dishes;
    private LocalDateTime startingTime;
    private double sum;

    public Order(Order order) {
        this.dishes = order.getDishes();
        this.startingTime = order.getStartingTime();
        this.sum = order.getSum();
    }

    public Order(Dishes dish, int qty) {
        if (dishes == null) {
            dishes = new ArrayList<>();
        }

        dishes.add(dish);
        dish.setQty(qty);
        sum += dish.calculateDishSum();
        this.startingTime = LocalDateTime.now();
    }

    public List<Dishes> getDishes() {
        return dishes;
    }

    public void addDish(Dishes dish, int qty) {
        dishes.add(dish);
        dish.setQty(qty);
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    @Override
    public String toString() {
        String res = " ";
        for (Dishes dish : dishes) {
            res += dish.toString() + "\n";
        }
        return res;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
