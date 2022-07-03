package com.senla.javacourse;

import com.senla.javacourse.controller.Builder;
import com.senla.javacourse.controller.MenuController;
import com.senla.javacourse.controller.Navigator;
import com.senla.javacourse.controller.entity.Menu;
import com.senla.javacourse.controller.impl.MenuControllerImpl;
import com.senla.javacourse.controller.impl.NavigatorImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;



@Configuration
@ComponentScan
public class AppConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("config.properties"));
        return configurer;
    }

    @Bean
    @Qualifier("rootMenu")
    public static Menu rootMenu(Builder builder) {
        builder.buildMenu();
        return builder.getRootMenu();
    }

    @Bean
    @Qualifier("navigator")
    public static Navigator navigator(@Qualifier("rootMenu") Menu rootMenu) {
        return new NavigatorImpl(rootMenu);
    }

    @Bean
    public static MenuController menuController(@Qualifier("navigator") Navigator navigator) {
        return new MenuControllerImpl(navigator);
    }
}
