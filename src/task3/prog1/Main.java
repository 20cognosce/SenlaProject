package task3.prog1;

import java.util.Random;

// Variant 4

public class Main {
    public static void main(String[] args) {
        Random randGenerator = new Random();
        int number = randGenerator.nextInt(900) + 100;

        String numberAsString = Integer.toString(number);
        int a = Character.getNumericValue(numberAsString.charAt(0));
        int b = Character.getNumericValue(numberAsString.charAt(1));
        int c = Character.getNumericValue(numberAsString.charAt(2));

        System.out.println(number + "\n" + (a + b + c));
    }
}
