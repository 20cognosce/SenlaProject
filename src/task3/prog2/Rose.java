package task3.prog2;

public class Rose extends Flower {
    private final boolean spiked;
    private static final int BASE_PRICE = 100;
    private int quantity;

    boolean isSpiked() {
        return this.spiked;
    }

    @Override
    public String toString() {
        String spikes = isSpiked() ? spikes = "Spiked" : "NotSpiked";
        return(this.getColor() + " " + spikes + " Rose");
    };

    public Rose(String color, Boolean isSpiked, int quantity) {
        super(color, (BASE_PRICE + isSpiked.compareTo(true) * 50), quantity);
        this.spiked = isSpiked;
    }
}
