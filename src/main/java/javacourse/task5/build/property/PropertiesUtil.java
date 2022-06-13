package javacourse.task5.build.property;

import javacourse.task5.build.config.Constants;

import java.io.FileInputStream;
import java.io.IOException;

public class PropertiesUtil {
    public static java.util.Properties property;
    public static FileInputStream fis;

    static {
        try {
            fis = new FileInputStream(Constants.configPropertiesPath);
            property = new java.util.Properties();
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
