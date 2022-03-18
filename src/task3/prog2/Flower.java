package task3.prog2;

public abstract class Flower {
    private int price;
    private final String color;
    private final int quantity;

    public Flower(String color, int price, int quantity) {
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }

    String getColor() {
        return this.color;
    }

    public int getPrice() {
        return price;
    }

    public int getTotalPrice() {
        return getPrice() * quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public abstract String toString();

    public int getQuantity() {
        return quantity;
    }
}
