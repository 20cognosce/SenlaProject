package task3.prog4;

import java.util.HashMap;

import static task3.prog4.RoomStatus.FREE;
import static task3.prog4.RoomStatus.UNDER_REPAIR;

public class Hotel {
    private final HashMap<Integer, Room> roomsTable = new HashMap<>();
    private final HashMap<String, Service> servicesTable = new HashMap<>();

    void loadRoomsDatabase() {
        Room room1 = new Room (2,1, FREE, 2000);
        Room room2 = new Room (4,2, FREE, 5000);
        Room room3 = new Room (3,3, UNDER_REPAIR, 3000);
        Room room4 = new Room (5,4, FREE, 7500);
        Room room5 = new Room (4,5, FREE, 6000);
        roomsTable.put(room1.getRoomNumber(), room1);
        roomsTable.put(room2.getRoomNumber(), room2);
        roomsTable.put(room3.getRoomNumber(), room3);
        roomsTable.put(room4.getRoomNumber(), room4);
        roomsTable.put(room5.getRoomNumber(), room5);
    }

    void loadServicesDatabase() {
        Service service1 = new Service("Завтрак в номер", 500);
        Service service2 = new Service("Обед в номер", 600);
        Service service3 = new Service("Ужин в номер", 800);
        servicesTable.put(service1.getName(), service1);
        servicesTable.put(service2.getName(), service2);
        servicesTable.put(service3.getName(), service3);
    }

    void addNewRoom(Room room) {
        roomsTable.put(room.getRoomNumber(), room);
    }
    void addNewService(Service service) {
        servicesTable.put(service.getName(), service);
    }

    Service getServiceByName(String serviceName) {
        return servicesTable.get(serviceName);
    }

    Room getRoomByNumber(Integer roomNumber) {
        return roomsTable.get(roomNumber);
    }
}
