package task5.config.json_util.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonReaderUtil {
    private static final  ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().enable(SerializationFeature.INDENT_OUTPUT);

    //абракадабра...
    public static <T> List<T> readConfig(File file, Class<T[]> entityClass) throws IOException {
        //Class<T>[] values = objectMapper.readValue(file, entityClass.getClass());
        T[] values = objectMapper.readValue(file, entityClass);
        System.out.println(entityClass.getCanonicalName() + " have been loaded successfully");
        return Arrays.asList(values);
    }
}
