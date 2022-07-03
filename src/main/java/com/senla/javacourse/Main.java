package com.senla.javacourse;

import com.senla.javacourse.controller.MenuController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);

        MenuController menuController = ctx.getBean(MenuController.class);
        menuController.run();
        ctx.close();
    }
}
