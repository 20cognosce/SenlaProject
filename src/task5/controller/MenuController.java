package task5.controller;

import java.util.Scanner;

public class MenuController {
    Builder builder = new Builder();
    Navigator navigator;

    void run() {
        builder.buildMenu();
        navigator = new Navigator(builder.getRootMenu());

        Scanner scanner = new Scanner(System.in);
        navigator.printMenu();
        int index;
        while (true) {
            index = scanner.nextInt();
            if (index == 0) return;

            try {
                navigator.navigate(index);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                navigator.printMenu();
                continue;
            }
            navigator.printMenu();
        }
    }
}
