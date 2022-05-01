package task5.controller;

import task5.config.DI.DependencyMenuAutowired;

import java.util.Locale;
import java.util.Objects;

public class Navigator {
    @DependencyMenuAutowired(menuClass = Menu.class)
    Menu currentMenu;
    int indexOfAction = -1;

    void doAction() {
        currentMenu.getMenuItems().get(indexOfAction).doAction();
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

        if (Objects.isNull(currentMenu.getMenuItems().get(index - 1).getNextMenu())) {
            this.indexOfAction = index - 1;
            throw new CantNavigateFurtherException();
        } else {
            currentMenu = currentMenu.getMenuItems().get(index - 1).getNextMenu();
        }
    }
}
























