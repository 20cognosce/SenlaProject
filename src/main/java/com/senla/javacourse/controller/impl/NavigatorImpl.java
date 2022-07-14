package com.senla.javacourse.controller.impl;

import com.senla.javacourse.controller.Navigator;
import com.senla.javacourse.controller.entity.Menu;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

@Component
public class NavigatorImpl implements Navigator {
    @Qualifier("rootMenu")
    private Menu currentMenu;
    private int indexOfAction = -1;

    public NavigatorImpl(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    @Override
    public void doAction() {
        currentMenu.getMenuItems().get(indexOfAction).doAction();
    }

    @Override
    public void printMenu() {
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

    @Override
    public void navigate(Integer index) throws CantNavigateFurtherException {
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