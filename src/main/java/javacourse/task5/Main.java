package javacourse.task5;

import javacourse.task5.build.factory.Application;
import javacourse.task5.build.factory.ApplicationContext;
import javacourse.task5.build.orm.OrmManagementUtil;
import javacourse.task5.controller.Builder;
import javacourse.task5.controller.MenuController;
import javacourse.task5.dao.entity.Maintenance;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run();
        Builder builder = context.getObject(Builder.class);
        builder.buildMenu();
        MenuController menuController = context.getObject(MenuController.class);
        menuController.run();
    }
}
