package task5.config;

import java.lang.annotation.*;

import static task5.config.ConfigFileEnum.DEFAULT_JSON;

@Repeatable(ConfigProperties.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigProperty {
    ConfigFileEnum configFileEnum() default DEFAULT_JSON;
    String propertyName() default "";
    Class<?> type() default String.class;
}
