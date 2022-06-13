package javacourse.task5.build.factory;

import javacourse.task5.build.DI.DependencyInjector;
import javacourse.task5.build.config.ConfigInjector;
import javacourse.task5.dao.AbstractDao;

import java.lang.reflect.InvocationTargetException;


public class ObjectFactory {
    private final ApplicationContext context;
    private final ConfigInjector configInjector;
    private final DependencyInjector dependencyInjector;

    public ObjectFactory(ApplicationContext context, ConfigInjector configInjector, DependencyInjector dependencyInjector) {
        this.context = context;
        this.configInjector = configInjector;
        this.dependencyInjector = dependencyInjector;
    }

    public <T> T createObject(Class<T> implClass) {
        T t;
        try {
            t = create(implClass);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (AbstractDao.class.isAssignableFrom(t.getClass())) {
            configInjector.injectDaoConfiguration(t);
        } else {
            dependencyInjector.injectDependency(t, context);
        }
        return t;
    }

    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }
}




