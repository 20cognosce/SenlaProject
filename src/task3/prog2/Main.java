package task3.prog2;

// Variant 1

public class Main {
    public static void main(String[] args) {
        Bouquet bouquet = new Bouquet();
        bouquet.add(new Rose("Red", true, 10));
        bouquet.add(new Rose("White", false, 1));

        bouquet.add(new Lily("White", 40, 2));
        bouquet.add(new Lily("Pink", 70, 5));

        System.out.println(bouquet);
        System.out.println("Total price: " + bouquet.getPrice());
    }
}
