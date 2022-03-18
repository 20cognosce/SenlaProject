package task4.prog1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static task4.prog1.RoomStatus.*;

public class Hotel {
    RoomManager roomManager = new RoomManager();
    ServiceManager serviceManager = new ServiceManager();
    GuestRoomManager guestRoomManager = new GuestRoomManager();

    private Hotel getOuter() {
        return Hotel.this;
    }

    public class RoomManager {
        private RoomManager(){}

        private final Map<Integer, Room> rooms = new LinkedHashMap<>();

        void loadRoomsDatabase() {
            Room room1 = new Room (1,2,3, FREE, 2000);
            Room room2 = new Room (2,5,4, FREE, 5000);
            Room room3 = new Room (3,3,4, UNDER_REPAIR, 3000);
            Room room4 = new Room (4,6,4, CLEANING, 7500);
            Room room5 = new Room (5,4,5, FREE, 8000);
            room5.setDetails("Огромное джакузи с видом на Москва Сити");
            rooms.put(room1.getRoomNumber(), room1);
            rooms.put(room2.getRoomNumber(), room2);
            rooms.put(room3.getRoomNumber(), room3);
            rooms.put(room4.getRoomNumber(), room4);
            rooms.put(room5.getRoomNumber(), room5);
        }

        void addNewRoom(Room room) {
            rooms.put(room.getRoomNumber(), room);
        }

        Room getRoomByNumber(Integer roomNumber) {
            return rooms.get(roomNumber);
        }

        String getRoomsAsString() {
            StringBuilder out = new StringBuilder();
            rooms.values().forEach(room -> out.append(room.toString()));
            return out.toString();
        }

        String getRoomsAsString(Map<Integer, Room> subTable) {
            StringBuilder out = new StringBuilder();
            subTable.values().forEach(room -> out.append(room.toString()));
            return out.toString();
        }

        public Map<Integer, Room> sortRooms(
                Map<Integer, Room> roomsTableToSort, Comparator<Room> comparator) {
            /*I tried a lot of options. HashMap sorts items by the key, so for saving sorting result I must use
             * LinkedHashMap. .collect(Collectors.toMap(...)) returns Map, which is unsuitable for LinkedHashMap.
             * It would work to use .collect(toList) and then forEach(roomsTable.put(room.getRoomNumber(), room)) by the way
             */

            Map<Integer, Room> sorted = new LinkedHashMap<>();
            roomsTableToSort.values().stream().sorted(comparator)
                    .forEach(room -> sorted.put(room.getRoomNumber(), room));
            return sorted;
        }

        Map<Integer, Room> getRooms() {
            return rooms;
        }

        Map<Integer, Room> getFreeRooms() {
            Map<Integer, Room> freeRooms = new LinkedHashMap<>();
            rooms.forEach((key, value) -> {
                if (value.getRoomCurrentStatus() == FREE) freeRooms.put(key, value);
            });
            return freeRooms;
        }

        Map<Integer, Room> getFreeRooms(LocalDateTime asAtSpecificDate) {
            Map<Integer, Room> freeRooms = new LinkedHashMap<>();
            Map<Integer, Room> rooms = getRooms();
            Map<Room, List<Guest>> roomsGuests = convertGuestsRoomsToRoomsGuests();

            rooms.values().forEach(room -> {
                if (!roomsGuests.containsKey(room)) {
                    freeRooms.put(room.getRoomNumber(), room);
                } else {
                    boolean isFree = true;
                    for (Guest guest : roomsGuests.get(room)) {
                        if (!guest.getCheckInTime().isAfter(asAtSpecificDate) &&
                                guest.getCheckOutTime().isAfter(asAtSpecificDate)) isFree = false;
                    }
                    if (isFree) freeRooms.put(room.getRoomNumber(), room);
                }
            });

            return freeRooms;
        }

        private Map<Room, List<Guest>> convertGuestsRoomsToRoomsGuests() {
            Map<Guest, Room> guestsRooms = getOuter().guestRoomManager.getGuestsRooms();
            return new LinkedHashMap<>(guestsRooms.entrySet().stream()
                    .collect(Collectors.groupingBy(Map.Entry::getValue)).values().stream()
                    .collect(Collectors.toMap(
                                    item -> item.get(0).getValue(),
                                    item -> new ArrayList<>(
                                            item.stream()
                                                    .map(Map.Entry::getKey)
                                                    .collect(Collectors.toList())
                                    )
                            )
                    )
            );
        }
    }

    public static class ServiceManager {
        private ServiceManager(){}

        private final Map<String, Service> services = new LinkedHashMap<>();

        void loadServicesDatabase() {
            Service service1 = new Service("Завтрак в номер", 500, ServiceCategory.LOCAL_FOOD);
            Service service2 = new Service("Обед в номер", 600, ServiceCategory.LOCAL_FOOD);
            Service service3 = new Service("Ужин в номер", 800, ServiceCategory.LOCAL_FOOD);
            Service service4 = new Service("Принести доставку в номер", 100, ServiceCategory.DELIVERY_FOOD);
            Service service5 = new Service("Дополнительный набор для душа", 200, ServiceCategory.ACCESSORIES);
            services.put(service1.getName(), service1);
            services.put(service2.getName(), service2);
            services.put(service3.getName(), service3);
            services.put(service4.getName(), service4);
            services.put(service5.getName(), service5);
        }

        void addNewService(Service service) {
            services.put(service.getName(), service);
        }

        Service getServiceByName(String name) {
            return services.get(name);
        }

        public String getServicesPriceList() {
            StringBuilder out = new StringBuilder();
            services.values().forEach(service -> out
                    .append(service.getName()).append("; Категория: ").append(service.getCategory())
                    .append("; Цена: ").append(service.getPrice()).append("\n"));
            return out.toString();
        }

        public String getServicesPriceList(Comparator<Service> comparator) {
            StringBuilder out = new StringBuilder();
            services.values().stream().sorted(comparator)
                    .forEach(service -> out.append(service.toString()).append("\n"));
            return out.toString();
        }

        public String getServicesOfGuest(Guest guest) {
            StringBuilder out = new StringBuilder("Гость: " + guest.getFullName() + "\nЗаказы:\n");
            guest.getOrderedServices().forEach((service) -> out.append(service.toString()).append("; Дата: ")
                    .append(service.getOrderTime()
                            .truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME))
                    .append("\n"));
            return out.toString();
        }

        public String getServicesOfGuest(Guest guest, Comparator<Service> comparator) {
            StringBuilder out = new StringBuilder("Гость: " + guest.getFullName() + "\nЗаказы:\n");
            guest.getOrderedServices().stream().sorted(comparator).forEach((service) -> out
                    .append(service.toString()).append("; Дата: ")
                    .append(service.getOrderTime()
                            .truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME))
                    .append("\n"));
            return out.toString();
        }
    }

    public static class GuestRoomManager {
        private GuestRoomManager(){}

        private final Map<Guest, Room> guestsRooms = new LinkedHashMap<>();

        void loadGuestsDatabase() {
            Guest guest1 = new Guest("Ivanov Ivan Ivanovich", "1111 222222",
                    LocalDateTime.of(2022, 3, 2, 14, 0),
                    LocalDateTime.of(2022, 3, 5, 12, 0));
            Guest guest2 = new Guest("Ivanova Maria Ivanovna", "3333 444444",
                    LocalDateTime.of(2022, 3, 2, 14, 0),
                    LocalDateTime.of(2022, 3, 5, 12, 0));
            Guest guest3 = new Guest("Petrov Petr Petrovich", "5555 666666",
                    LocalDateTime.of(2022, 3, 1, 14, 0),
                    LocalDateTime.of(2022, 3, 14, 12, 0));
            Guest guest4 = new Guest("Abramov Nikita Alexandrovich", "7777 888888",
                    LocalDateTime.of(2022, 3, 1, 14, 0),
                    LocalDateTime.of(2022, 4, 1, 12, 0));
            Guest guest5 = new Guest("Yakovleva Margarita Vladimirovna", "9999 000000",
                    LocalDateTime.of(2022, 3, 1, 14, 0),
                    LocalDateTime.of(2022, 3, 14, 12, 0));
            guestsRooms.put(guest1, null);
            guestsRooms.put(guest2, null);
            guestsRooms.put(guest3, null);
            guestsRooms.put(guest4, null);
            guestsRooms.put(guest5, null);
        }

        Map<Guest, Room> getGuestsRooms() {
            return guestsRooms;
        }

        void addGuest(Room room, Guest guest) {
            try {
                room.addGuest(guest);
                guestsRooms.put(guest, room);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        void removeGuest(Guest guest) {
            try {
                guestsRooms.get(guest).removeGuest(guest);
                guestsRooms.remove(guest);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        String getGuestsRoomsAsString(Map<Guest, Room> subTable) {
            StringBuilder out = new StringBuilder();
            subTable.forEach((key, value) -> out.append("Гость: ").append(key.getFullName())
                    .append("; Номер: ").append(value.getRoomNumber())
                    .append("; Дата заезда: ").append(key.getCheckInTime())
                    .append("; Дата освобождения: ").append(key.getCheckOutTime()).append("\n"));
            return out.toString();
        }

        public Map<Guest, Room> sortGuestsRooms(Map<Guest, Room> roomsTableToSort,
                                                          Comparator<Guest> comparator) {
            Map<Guest, Room> sorted = new LinkedHashMap<>();
            roomsTableToSort.keySet().stream().sorted(comparator)
                    .forEach(guest -> sorted.put(guest, guestsRooms.get(guest)));
            return sorted;
        }

        public Guest getGuestByName(String fullName) throws NoSuchElementException {
            Guest result = guestsRooms.keySet().stream()
                    .filter(guest -> (guest.getFullName().equals(fullName)))
                    .findFirst().orElse(null);
            if (result == null) {
                throw new NoSuchElementException();
            }
            return result;
        }
    }
}















