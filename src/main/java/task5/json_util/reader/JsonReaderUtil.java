package task5.json_util.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import task5.dao.entity.Guest;
import task5.dao.entity.Maintenance;
import task5.dao.entity.Room;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonReaderUtil {
    private static final  ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().enable(SerializationFeature.INDENT_OUTPUT);

    public static List<Room> loadRooms(File roomFile) throws IOException {
        Room[] rooms = objectMapper.readValue(roomFile, Room[].class);
        System.out.println("Rooms have been loaded successfully: "+ rooms.length);
        return Arrays.asList(rooms);
    }

    public static List<Guest> loadGuests(File guestFile) throws IOException {
        Guest[] guests = objectMapper.readValue(guestFile, Guest[].class);
        System.out.println("Guests have been loaded successfully: "+ guests.length);
        return Arrays.asList(guests);
    }

    public static List<Maintenance> loadMaintenances(File maintenanceFile) throws IOException {
        Maintenance[] maintenances = objectMapper.readValue(maintenanceFile, Maintenance[].class);
        System.out.println("Maintenances have been loaded successfully: " + maintenances.length);
        return Arrays.asList(maintenances);
    }
}
