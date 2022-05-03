package task5.build.DI;

import task5.build.config.ConfigInjector;
import task5.build.factory.ApplicationContext;
import task5.controller.Builder;
import task5.controller.Navigator;
import task5.service.impl.AbstractServiceImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DependencyInjector {
        public void injectDependency(Object bean, ApplicationContext context) {
        Class<?> beanClass = bean.getClass();
        List<Field> declaredFields = ConfigInjector.getAllFields(new ArrayList<>(), beanClass);

        declaredFields.stream()
            .filter(field -> field.isAnnotationPresent(DependencyAutowired.class))
            .forEach(field -> {
                field.setAccessible(true);
                Class<?> dependencyClass = field.getAnnotation(DependencyAutowired.class).dependencyClass();
                Object value;

                //dao for Services searching
                if (AbstractServiceImpl.class.isAssignableFrom(bean.getClass())) {
                    if (String.class.equals(dependencyClass)) {
                        try {
                            dependencyClass = findClassTypeFromAbstractServiceField(bean);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                }

                //rootMenu for Navigator searching
                if (Navigator.class.isAssignableFrom(bean.getClass())) {
                    value = context.getObject(Builder.class).getRootMenu();
                } else {
                    value = context.getObject(dependencyClass);
                }

                try {
                    field.set(bean, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    private Class<?> findClassTypeFromAbstractServiceField(Object serviceImplBean) throws NoSuchFieldException, IllegalAccessException {
        try {
            Field daoClassField = serviceImplBean.getClass().getSuperclass().getDeclaredField("typeParameterClass");
            daoClassField.setAccessible(true);
            return ((Class<?>) daoClassField.get(serviceImplBean));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

