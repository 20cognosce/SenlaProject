package javacourse.task5.build.factory;

public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> interfaceClass);
}
