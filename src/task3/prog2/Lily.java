package task3.prog2;

public class Lily extends Flower {
    private final int length;
    private static final int BASE_PRICE = 500;

    public int getLength() {
        return this.length;
    }

    @Override
    public String toString() {
        return(this.getColor() + " " + this.getLength() + "cm" + " Lily");
    };

    public Lily(String color, int length, int quantity) {
        super(color, (BASE_PRICE + length * 10), quantity);
        this.length = length;
    }
}
