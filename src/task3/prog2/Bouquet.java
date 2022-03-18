package task3.prog2;

import java.util.ArrayList;
import java.util.List;

public class Bouquet {
    private final List<Flower> currentBouquet = new ArrayList<>();

    void add(Flower flower) {
        currentBouquet.add(flower);
    }
    void remove(Flower flower) {
        currentBouquet.remove(flower);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        currentBouquet.forEach(flower -> out.append(flower.toString()).append(" ").append(flower.getPrice())
                .append(" ").append(flower.getQuantity()).append("\n"));
        return out.toString();
    }

    public int getPrice() {
        return currentBouquet.stream().mapToInt(Flower::getTotalPrice).sum();
    }
}
