package task5.build.factory;

import task5.build.DI.DependencyInjector;
import task5.build.config.ConfigInjector;

import java.util.Map;

public class Application {
    public static ApplicationContext run(Map<Class, Class> interface2ImplClass) {
        BuildConfig config = new BuildConfig(interface2ImplClass);
        ApplicationContext context = new ApplicationContext(config);
        ConfigInjector configInjector = new ConfigInjector();
        DependencyInjector dependencyInjector = new DependencyInjector();
        ObjectFactory objectFactory = new ObjectFactory(context, configInjector, dependencyInjector);
        context.setFactory(objectFactory);
        return context;
    }
}
