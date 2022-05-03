package task5.build.json.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class JsonWriterUtil {
    private static final  ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().enable(SerializationFeature.INDENT_OUTPUT);

    public static <T> void writeConfig(List<T> list, File file) throws IOException {
        objectMapper.writeValue(file, list);
    }
}
