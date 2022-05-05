package task5;

import task5.build.factory.Application;
import task5.build.factory.ApplicationContext;
import task5.controller.Builder;
import task5.controller.MenuController;
import task5.controller.impl.BuilderImpl;
import task5.dao.entity.Guest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run();

        Builder builder = context.getObject(Builder.class);
        builder.buildMenu();
        MenuController menuController = context.getObject(MenuController.class);
        menuController.run();
    }
}
