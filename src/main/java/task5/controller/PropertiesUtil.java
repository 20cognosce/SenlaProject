package task5.controller;

import java.io.FileInputStream;
import java.io.IOException;

public class PropertiesUtil {
    public static java.util.Properties property;
    public static FileInputStream fis;

    static {
        try {
            fis = new FileInputStream("src/main/java/task5/resources/config.properties");
            property = new java.util.Properties();
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
