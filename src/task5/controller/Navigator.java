package task5.controller;

import java.util.Locale;
import java.util.Objects;

public class Navigator {
    Menu currentMenu;

    Navigator(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    void printMenu(){
        var ref = new Object() {
            int i = 1;
        };
        System.out.println("-----------------------------------------------------------");
        System.out.println(currentMenu.getName().toUpperCase(Locale.ROOT).replace("", " ").trim());
        System.out.println("-1 - Вернуться назад\n 0 - Выход\n");
        currentMenu.getMenuItems().forEach(menuItem -> {
            System.out.println(ref.i + ". "  + menuItem.getTitle());
            ref.i++;
        });
    }

    void navigate(Integer index) throws IndexOutOfBoundsException {
        if (index == -1) {
            Menu previousMenu = currentMenu.getMenuItems().get(0).getPreviousMenu();
            if (Objects.isNull(previousMenu)) {
                return;
            }
            currentMenu = previousMenu;
            return;
        }

        if (Objects.isNull(currentMenu.getMenuItems().get(index - 1))) {
            throw new IndexOutOfBoundsException("Incorrect menu item's index");
        }

        if (Objects.isNull(currentMenu.getMenuItems().get(index - 1).getNextMenu())) {
            try {
                currentMenu.getMenuItems().get(index - 1).doAction();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Press Enter key to continue...");
            try {
                var temp = System.in.read();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            currentMenu = currentMenu.getMenuItems().get(index - 1).getNextMenu();
        }
    }
}
























