package task5.json_util.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import task5.dao.entity.Guest;
import task5.dao.entity.Maintenance;
import task5.dao.entity.Room;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class JsonWriterUtil {
    private static final  ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().enable(SerializationFeature.INDENT_OUTPUT);

    public static void saveRooms(List<Room> list, File roomFile) throws IOException {
        objectMapper.writeValue(roomFile, list);
    }

    public static void saveGuests(List<Guest> list, File guestFile) throws IOException {
        objectMapper.writeValue(guestFile, list);
    }

    public static void saveMaintenances(List<Maintenance> list, File maintenanceFile) throws IOException {
        objectMapper.writeValue(maintenanceFile, list);
    }
}
