public class Item {
    private String name;
    private double price;
    private int bulkQuantity;
    private double bulkPrice;

    public Item(String name, double price) {
        this(name, price, 0, 0.0);
    }

    public Item(String name, double price, int bulkQuantity, double bulkPrice) {
        if (price < 0 || bulkQuantity < 0 || bulkPrice < 0)
            throw new IllegalArgumentException();
        this.name = name;
        this.price = price;
        this.bulkQuantity = bulkQuantity;
        this.bulkPrice = bulkPrice;
    }

    public double priceFor(int quantity) {
        if (bulkQuantity > 0 && quantity >= bulkQuantity) {
            return quantity / bulkQuantity * bulkPrice * bulkQuantity + quantity % bulkQuantity * price;
        }
        return quantity * price;
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Item) obj).name);
    }

    @Override
    public String toString() {
        return this.name + ", $" + this.price + (this.bulkQuantity > 0 ? " (bulk buy " + this.bulkQuantity + " for " + this.bulkPrice + " each)" : "");
    }
}
