package javacourse.task5.build.DI;

import javacourse.task5.build.config.ConfigInjector;
import javacourse.task5.build.factory.ApplicationContext;
import javacourse.task5.controller.Builder;
import javacourse.task5.controller.entity.Menu;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DependencyInjector {
        public void injectDependency(Object bean, ApplicationContext context) {
        Class<?> beanClass = bean.getClass();
        List<Field> declaredFields = ConfigInjector.getAllFields(new ArrayList<>(), beanClass);

        declaredFields.stream()
            .filter(field -> field.isAnnotationPresent(Autowired.class))
            .forEach(field -> {
                field.setAccessible(true);
                Object value;

                if (Menu.class.isAssignableFrom(field.getType())) {
                    //rootMenu of Builder for Navigator search
                    value = context.getObject(Builder.class).getRootMenu();
                } else {
                    value = context.getObject(field.getType());
                }

                try {
                    field.set(bean, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }
}

