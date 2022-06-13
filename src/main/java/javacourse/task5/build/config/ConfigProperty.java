package javacourse.task5.build.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static javacourse.task5.build.config.ConfigFileEnum.DEFAULT_JSON;

@Repeatable(ConfigProperties.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigProperty {
    ConfigFileEnum configFileEnum() default DEFAULT_JSON;
    String propertyName() default "";
    Class<?> type() default String.class;
}
