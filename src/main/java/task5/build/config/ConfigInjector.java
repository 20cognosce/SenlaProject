package task5.build.config;

import task5.build.json.reader.JsonReaderUtil;
import task5.dao.AbstractDao;
import task5.dao.impl.AbstractDaoImpl;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigInjector {

    public void injectDaoConfiguration(Object bean) {
        if (!AbstractDao.class.isAssignableFrom(bean.getClass())) {
            throw new IllegalArgumentException("Passed bean is not dao instance: " + bean.getClass());
        }

        Class<?> beanClass = bean.getClass();
        List<Field> declaredFields = getAllFields(new ArrayList<>(), beanClass);

        declaredFields.forEach(field -> {
            field.setAccessible(true);
            ConfigProperty[] annotations = field.getAnnotationsByType(ConfigProperty.class);

            Arrays.stream(annotations).forEach(configProperty -> {
                try {
                    Class<?> type = ((AbstractDao<?>) bean).getDaoEntity().getClass();
                    if (type == configProperty.type().getComponentType()) {
                        injectFieldConfig(bean, field, configProperty);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private <T> void injectFieldConfig(Object bean, Field field, ConfigProperty configProperty) {
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

    public static List<Field> getAllFields(List<Field> fields, Class<?> objectClass) {
        fields.addAll(Arrays.asList(objectClass.getDeclaredFields()));
        if (objectClass.getSuperclass() != null) {
            getAllFields(fields, objectClass.getSuperclass());
        }
        return fields;
    }
}
