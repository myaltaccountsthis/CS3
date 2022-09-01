import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<ItemOrder> orders;

    public ShoppingCart() {
        this.orders = new ArrayList<>();
    }

    public void add(ItemOrder newOrder) {
        this.orders.remove(newOrder);
        this.orders.add(newOrder);
    }

    public double getTotal() {
        double total = 0.0;
        for (ItemOrder order : orders) {
            total += order.getPrice();
        }
        return total;
    }
}
