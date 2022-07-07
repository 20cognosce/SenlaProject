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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@Configuration
@ComponentScan
@EnableTransactionManagement
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

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(getClass().getPackage().getName()); //"com.senla.javacourse"
        factory.setDataSource(dataSource());
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName("org.postgresql.Driver");
        driver.setUrl("jdbc:postgresql://localhost/senla_project");
        driver.setUsername("postgres");
        driver.setPassword("password123");
        return driver;
    }

    /*
    @Bean
    public static PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }*/
}
