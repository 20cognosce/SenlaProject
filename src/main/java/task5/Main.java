package task5;

import task5.build.factory.Application;
import task5.build.factory.ApplicationContext;
import task5.controller.Builder;
import task5.controller.MenuController;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run();

        Builder builder = context.getObject(Builder.class);
        builder.buildMenu();
        MenuController menuController = context.getObject(MenuController.class);
        menuController.run();
    }
}
