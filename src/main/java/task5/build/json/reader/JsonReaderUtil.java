package task5.build.json.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonReaderUtil {
    private static final  ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules()
            .enable(SerializationFeature.INDENT_OUTPUT);

    //абракадабра...
    public static <T> List<T> readConfig(File file, Class<T[]> entityClass) throws IOException {
        T[] values = objectMapper.readValue(file, entityClass);
        System.out.println(values.getClass().getCanonicalName() + " have been loaded successfully");
        return new ArrayList<>(Arrays.asList(values)); //important!!!
    }
}
