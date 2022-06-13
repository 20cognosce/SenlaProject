package javacourse.task5.build.factory;

import javacourse.task5.build.DI.DependencyInjector;
import javacourse.task5.build.config.ConfigInjector;

public class Application {
    public static ApplicationContext run() {
        BuildConfig config = new BuildConfig("javacourse");
        ApplicationContext context = new ApplicationContext(config);
        ConfigInjector configInjector = new ConfigInjector();
        DependencyInjector dependencyInjector = new DependencyInjector();
        ObjectFactory objectFactory = new ObjectFactory(context, configInjector, dependencyInjector);
        context.setFactory(objectFactory);
        return context;
    }
}
