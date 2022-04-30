package task5.config;

import task5.config.json_util.reader.JsonReaderUtil;
import task5.dao.impl.AbstractDaoImpl;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigInstaller {
    public void installConfiguration(Object bean) {
        Class<?> beanClass = bean.getClass();
        List<Field> declaredFields = getAllFields(new ArrayList<>(), beanClass);

        declaredFields.forEach(field -> {
            field.setAccessible(true);
            ConfigProperty[] annotations = field.getAnnotationsByType(ConfigProperty.class);

            Arrays.stream(annotations).forEach(configProperty -> {
                if (AbstractDaoImpl.class.isAssignableFrom(bean.getClass())) {
                    try {
                        /* я вынужден хранить класс сущности в AbstractDao,
                        иначе я не знаю какая именно имплементация сейчас конфигурируется */
                        Field classField = bean.getClass().getSuperclass().getDeclaredField("typeParameterClassArray");
                        classField.setAccessible(true);
                        Class<?> type = ((Class<?>) classField.get(bean)).getComponentType();

                        if (type == configProperty.type().getComponentType()) {
                            installFieldConfig(bean, field, configProperty);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    //Пока работает только для репозиториев в DAO
    @SuppressWarnings("unchecked сast")
    private <T> void installFieldConfig(Object bean, Field field, ConfigProperty configProperty) {
        if (configProperty.type().isArray()) {
            try {
                File file = configProperty.configFileEnum().getConfigFile();
                Class<T[]> type = (Class<T[]>) configProperty.type();
                Object valueList = JsonReaderUtil.readConfig(file, type);

                if (AbstractDaoImpl.class.isAssignableFrom(bean.getClass())) {
                    field.set(bean, valueList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<Field> getAllFields(List<Field> fields, Class<?> objectClass) {
        fields.addAll(Arrays.asList(objectClass.getDeclaredFields()));
        if (objectClass.getSuperclass() != null) {
            getAllFields(fields, objectClass.getSuperclass());
        }
        return fields;
    }
}
