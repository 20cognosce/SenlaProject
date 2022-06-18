package javacourse.task5.build.config;

import javacourse.task5.build.json.reader.JsonReaderUtil;
import javacourse.task5.build.orm.DatabaseOrmManager;
import javacourse.task5.dao.AbstractDao;
import javacourse.task5.dao.impl.AbstractDaoImpl;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigInjector {

    public void injectDaoConfiguration(Object beanDao) {
        if (!AbstractDao.class.isAssignableFrom(beanDao.getClass())) {
            throw new IllegalArgumentException("Passed beanDao is not dao instance: " + beanDao.getClass());
        }

        Class<?> beanClass = beanDao.getClass();
        List<Field> declaredFields = getAllFields(new ArrayList<>(), beanClass);

        declaredFields.forEach(field -> {
            field.setAccessible(true);
            ConfigProperty[] annotations = field.getAnnotationsByType(ConfigProperty.class);

            Arrays.stream(annotations).forEach(configProperty -> {
                try {
                    Class<?> type = ((AbstractDao<?>) beanDao).getDaoEntity().getClass();
                    if (type == configProperty.type().getComponentType()) {
                        //injectFieldConfigFromJson(beanDao, field, configProperty);
                        injectFieldConfigFromHibernate(beanDao, field, configProperty);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private <T> void injectFieldConfigFromJson(Object bean, Field field, ConfigProperty configProperty) {
        if (configProperty.type().isArray()) {
            try {
                File file = configProperty.configFileEnum().getConfigFile();
                Class<T[]> type = (Class<T[]>) configProperty.type();
                List<T> valueList = JsonReaderUtil.readConfig(file, type);

                if (AbstractDaoImpl.class.isAssignableFrom(bean.getClass())) {
                    field.set(bean, valueList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private <T> void injectFieldConfigFromHibernate(Object bean, Field field, ConfigProperty configProperty) {
        if (configProperty.type().isArray()) {
            try {
                Class<T[]> type = (Class<T[]>) configProperty.type();
                Class<T> clazz = (Class<T>) type.getComponentType();
                List<T> valueList = DatabaseOrmManager.getListFromDatabase(clazz);

                if (AbstractDaoImpl.class.isAssignableFrom(bean.getClass())) {
                    field.set(bean, valueList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Field> getAllFields(List<Field> fields, Class<?> objectClass) {
        fields.addAll(Arrays.asList(objectClass.getDeclaredFields()));
        if (objectClass.getSuperclass() != null) {
            getAllFields(fields, objectClass.getSuperclass());
        }
        return fields;
    }
}
