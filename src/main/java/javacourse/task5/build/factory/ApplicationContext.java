package javacourse.task5.build.factory;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private ObjectFactory factory;
    private final Map<Class<?>, Object> cache = new ConcurrentHashMap<>();
    private final Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }

        T t = factory.createObject(implClass);
        if (implClass.isAnnotationPresent(Component.class)) {
            cache.put(type, t);
        }

        return t;
    }

    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }
}
