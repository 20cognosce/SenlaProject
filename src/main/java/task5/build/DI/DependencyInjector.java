package task5.build.DI;

import task5.build.config.ConfigInjector;
import task5.build.factory.ApplicationContext;
import task5.controller.Builder;
import task5.controller.entity.Menu;
import task5.dao.AbstractDao;

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

                //currentDao of service search
                if (AbstractDao.class.isAssignableFrom(field.getType())) {
                    try {
                        value = context.getObject(field.getType());
                    } catch (RuntimeException e) { //triggers when field is currentDao -> has more than 1 impl
                        try {
                            value = context.getObject(findClassTypeFromAbstractServiceField(bean));
                        } catch (Exception exc) {
                            exc.printStackTrace();
                            return;
                        }
                    }
                } else if (Menu.class.isAssignableFrom(field.getType())) {
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

