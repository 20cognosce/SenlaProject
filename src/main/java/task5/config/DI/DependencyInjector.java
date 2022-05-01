package task5.config.DI;

import task5.config.CI.ConfigInjector;
import task5.controller.Builder;
import task5.controller.MenuController;
import task5.controller.Navigator;
import task5.service.impl.AbstractServiceImpl;

import java.lang.reflect.Field;
import java.util.*;

public class DependencyInjector {
    private final Object root;
    public DependencyInjector(Object root) {
        this.root = root;
    }

    public void injectDependency(@DependencyInjectable Object bean) {
        Class<?> beanClass = bean.getClass();
        List<Field> declaredFields = ConfigInjector.getAllFields(new ArrayList<>(), beanClass);

        if (AbstractServiceImpl.class.isAssignableFrom(bean.getClass())) {
            injectServiceDependency(bean, declaredFields);
        }

        if (Builder.class.isAssignableFrom(bean.getClass())) {
            injectBuilderDependency(bean, declaredFields);
        }

        if (Navigator.class.isAssignableFrom(bean.getClass())) {
            injectNavigatorDependency(bean, declaredFields);
        }

        if (MenuController.class.isAssignableFrom(bean.getClass())) {
            injectMenuControllerDependency(bean, declaredFields);
        }
    }

    private void injectServiceDependency(Object bean, List<Field> declaredFields) {
        declaredFields.stream()
            .filter(field -> field.isAnnotationPresent(DependencyDaoAutowired.class))
            .forEach(field -> {
                field.setAccessible(true);
                Class<?> daoClass = field.getAnnotation(DependencyDaoAutowired.class).daoClass();
                Field daoRootField;

                if (String.class.equals(daoClass)) {
                    try {
                        daoClass = findClassTypeFromAbstractServiceField(bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }

                Class<?> finalDaoClass = daoClass;
                Optional<Field> daoRootFieldOptional = Arrays.stream(root.getClass().getDeclaredFields())
                        .filter(launcherComponentField -> finalDaoClass.equals(launcherComponentField.getType()))
                        .findFirst();

                try {
                    if (daoRootFieldOptional.isPresent()) {
                        daoRootField = daoRootFieldOptional.get();
                    } else {
                        throw new NoSuchElementException();
                    }
                    field.set(bean, daoRootField.get(root));
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

    private void injectBuilderDependency(Object bean, List<Field> declaredFields) {
        declaredFields.stream()
            .filter(field -> field.isAnnotationPresent(DependencyServiceAutowired.class))
            .forEach(field -> {
                field.setAccessible(true);
                Class<?> serviceClass = field.getAnnotation(DependencyServiceAutowired.class).serviceClass();
                Field serviceRootField;

                Optional<Field> serviceRootFieldOptional = Arrays.stream(root.getClass().getDeclaredFields())
                        .filter(launcherComponentField -> serviceClass.equals(launcherComponentField.getType()))
                        .findFirst();

                try {
                    if (serviceRootFieldOptional.isPresent()) {
                        serviceRootField = serviceRootFieldOptional.get();
                    } else {
                        throw new NoSuchElementException();
                    }
                    field.set(bean, serviceRootField.get(root));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    private void injectNavigatorDependency(Object bean, List<Field> declaredFields) {
        declaredFields.stream()
            .filter(field -> field.isAnnotationPresent(DependencyMenuAutowired.class))
            .forEach(field -> {
                field.setAccessible(true);
                Field builderRootField;
                Object builderRootObject;
                Field rootMenuBuilderField;

                Optional<Field> builderRootFieldOptional = Arrays.stream(root.getClass().getDeclaredFields())
                        .filter(launcherComponentField -> Builder.class.equals(launcherComponentField.getType()))
                        .findFirst();

                if (builderRootFieldOptional.isPresent()) {
                    builderRootField = builderRootFieldOptional.get();
                    builderRootField.setAccessible(true);
                    try {
                        builderRootObject = builderRootField.get(root);
                        rootMenuBuilderField = builderRootObject.getClass().getDeclaredField("rootMenu");
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                } else {
                    try {
                        throw new NullPointerException("Field with class Builder not found");
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }

                try {
                    rootMenuBuilderField.setAccessible(true);
                    field.set(bean, rootMenuBuilderField.get(builderRootObject));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    private void injectMenuControllerDependency(Object bean, List<Field> declaredFields) {
        declaredFields.stream()
            .filter(field -> field.isAnnotationPresent(DependencyNavigatorAutowired.class))
            .forEach(field -> {
                field.setAccessible(true);
                Class<?> navigatorClass = field.getAnnotation(DependencyNavigatorAutowired.class).navigatorClass();
                Field navigatorRootField;

                Optional<Field> navigatorRootFieldOptional = Arrays.stream(root.getClass().getDeclaredFields())
                        .filter(launcherComponentField -> navigatorClass.equals(launcherComponentField.getType()))
                        .findFirst();

                try {
                    if (navigatorRootFieldOptional.isPresent()) {
                        navigatorRootField = navigatorRootFieldOptional.get();
                    } else {
                        throw new NoSuchElementException();
                    }
                    field.set(bean, navigatorRootField.get(root));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }
}

