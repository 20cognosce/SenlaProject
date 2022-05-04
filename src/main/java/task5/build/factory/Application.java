package task5.build.factory;

import task5.build.DI.DependencyInjector;
import task5.build.config.ConfigInjector;

public class Application {
    public static ApplicationContext run() {
        BuildConfig config = new BuildConfig("task5");
        ApplicationContext context = new ApplicationContext(config);
        ConfigInjector configInjector = new ConfigInjector();
        DependencyInjector dependencyInjector = new DependencyInjector();
        ObjectFactory objectFactory = new ObjectFactory(context, configInjector, dependencyInjector);
        context.setFactory(objectFactory);
        return context;
    }
}
