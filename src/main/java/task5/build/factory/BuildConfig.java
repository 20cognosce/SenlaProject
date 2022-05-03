package task5.build.factory;


import java.util.Map;

public class BuildConfig implements Config {

    private final Map<Class, Class> interface2ImplClass;

    public BuildConfig(Map<Class, Class> interface2ImplClass) {
        this.interface2ImplClass = interface2ImplClass;
    }

    @Override
    public <T> Class getImplClass(Class<?> interfaceClass) {
        return interface2ImplClass.get(interfaceClass);
    }
}












