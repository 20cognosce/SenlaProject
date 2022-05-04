package task5.build.factory;


import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.*;

public class BuildConfig implements Config {
    private final Reflections reflections;
    private final Map<Class<?>, List<Class<?>>> interface2ImplClass;

    public BuildConfig(String packageToScan) {
        this.reflections = new Reflections(packageToScan, new SubTypesScanner(false));
        this.interface2ImplClass = buildInterfaceToImplMap();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Class<? extends T> getImplClass(Class<T> interfaceClass) {
        List<Class<?>> implementations = interface2ImplClass.get(interfaceClass);

        if (Objects.isNull(implementations)) {
            throw new RuntimeException(interfaceClass
                    + ": no implementation annotated with 'Component' found for this interface");
        } else if (implementations.size() != 1) {
            throw new RuntimeException(interfaceClass
                    + ": has more than one impl please update your config");
        } else {
            return (Class<? extends T>) implementations.get(0);
        }
    }

    private Map<Class<?>, List<Class<?>>> buildInterfaceToImplMap() {
        Set<Class<?>> allClassSet = new HashSet<>(reflections.getSubTypesOf(Object.class));
        Map<Class<?>, List<Class<?>>> ifc2ImplClass = new HashMap<>();
        allClassSet.forEach(projectClass -> {
            if (projectClass.isInterface()) {
                reflections.getSubTypesOf(projectClass).forEach(implClass -> {
                    if (implClass.isAnnotationPresent(Component.class)) {
                        List<Class<?>> implementations;
                        if (Objects.isNull(ifc2ImplClass.get(projectClass))) {
                            implementations = new ArrayList<>();
                        } else {
                            implementations = new ArrayList<>(ifc2ImplClass.get(projectClass));
                        }
                        implementations.add(implClass);
                        ifc2ImplClass.put(projectClass, implementations);
                    }
                });
            }
        });

        return ifc2ImplClass;
    }
}












