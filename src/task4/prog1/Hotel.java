package task4.prog1;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static task4.prog1.RoomStatus.*;

public class Hotel {
    RoomManager roomManager = new RoomManager();
    MaintenanceManager serviceManager = new MaintenanceManager();
    GuestManager guestManager = new GuestManager();

    public static class RoomManager {
        private RoomManager(){}

        private final List<Room> rooms = new ArrayList<>();

        void loadRoomsDatabase() {
            Room room1 = new Room (1,2,3, FREE, 2000);
            Room room2 = new Room (2,5,4, FREE, 5000);
            Room room3 = new Room (3,3,4, UNDER_REPAIR, 3000);
            Room room4 = new Room (4,6,4, CLEANING, 7500);
            Room room5 = new Room (5,4,5, FREE, 8000);
            room5.setDetails("Огромное джакузи с видом на Москва Сити");
            rooms.add(room1);
            rooms.add(room2);
            rooms.add(room3);
            rooms.add(room4);
            rooms.add(room5);
        }

        void addNewRoom(Room room) {
            rooms.add(room);
        }

        Room getRoomByNumber(Integer roomNumber) throws NoSuchElementException {
            Room result = rooms.stream()
                    .filter(room -> (roomNumber.equals(room.getRoomNumber())))
                    .findFirst().orElse(null);
            if (result == null) {
                throw new NoSuchElementException();
            }
            return result;
        }

        String getRoomsAsString() {
            StringBuilder out = new StringBuilder();
            rooms.forEach(room -> out.append(room.toString()));
            return out.toString();
        }

        String getRoomsAsString(List<Room> subList) {
            StringBuilder out = new StringBuilder();
            subList.forEach(room -> out.append(room.toString()));
            return out.toString();
        }

        public List<Room> sortRooms(
                List<Room> roomsListToSort, Comparator<Room> comparator) {
            List<Room> sorted = new ArrayList<>();
            roomsListToSort.stream().sorted(comparator)
                    .forEach(sorted::add);
            return sorted;
        }

        List<Room> getRooms() {
            return rooms;
        }

        List<Room> getFreeRooms() {
            List<Room> freeRooms = new ArrayList<>();
            rooms.forEach((room) -> {
                if (room.getRoomCurrentStatus() == FREE) freeRooms.add(room);
            });
            return freeRooms;
        }

        List<Room> getFreeRooms(LocalDateTime asAtSpecificDate) {
            List<Room> freeRooms = new ArrayList<>();
            List<Room> rooms = getRooms();

            rooms.forEach(room -> {
                boolean isFree = true;
                for (Guest guest : room.getGuestsCurrentList()) {
                    if (!guest.getCheckInTime().isAfter(asAtSpecificDate) &&
                            guest.getCheckOutTime().isAfter(asAtSpecificDate)) isFree = false;
                }
                if (isFree) freeRooms.add(room);
            });

            return freeRooms;
        }
    }

    public static class MaintenanceManager {
        private MaintenanceManager(){}

        private final List<Maintenance> services = new ArrayList<>();

        void loadServicesDatabase() {
            Maintenance service1 = new Maintenance("Завтрак в номер", 500, MaintenanceCategory.LOCAL_FOOD);
            Maintenance service2 = new Maintenance("Обед в номер", 600, MaintenanceCategory.LOCAL_FOOD);
            Maintenance service3 = new Maintenance("Ужин в номер", 800, MaintenanceCategory.LOCAL_FOOD);
            Maintenance service4 = new Maintenance("Принести доставку в номер", 100, MaintenanceCategory.DELIVERY_FOOD);
            Maintenance service5 = new Maintenance("Дополнительный набор для душа", 200, MaintenanceCategory.ACCESSORIES);
            services.add(service1);
            services.add(service2);
            services.add(service3);
            services.add(service4);
            services.add(service5);
        }

        void addNewMaintenance(Maintenance service) {
            services.add(service);
        }

        Maintenance getServiceByName(String name) throws NoSuchElementException {
            Maintenance result = services.stream()
                    .filter(service -> (name.equals(service.getName())))
                    .findFirst().orElse(null);
            if (result == null) {
                throw new NoSuchElementException();
            }
            return result;
        }

        public String getServicesPriceList() {
            StringBuilder out = new StringBuilder();
            services.forEach(service -> out
                    .append(service.getName()).append("; Категория: ").append(service.getCategory())
                    .append("; Цена: ").append(service.getPrice()).append("\n"));
            return out.toString();
        }

        public String getServicesPriceList(Comparator<Maintenance> comparator) {
            StringBuilder out = new StringBuilder();
            services.stream().sorted(comparator)
                    .forEach(service -> out.append(service.toString()).append("\n"));
            return out.toString();
        }

        public String getMaintenancesOfGuest(Guest guest) {
            StringBuilder out = new StringBuilder("Гость: " + guest.getFullName() + "\nЗаказы:\n");
            guest.getOrderedServices().forEach((service) -> out.append(service.toString()).append("; Дата: ")
                    .append(service.getOrderTime()
                            .truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME))
                    .append("\n"));
            return out.toString();
        }

        public String getMaintenancesOfGuest(Guest guest, Comparator<Maintenance> comparator) {
            StringBuilder out = new StringBuilder("Гость: " + guest.getFullName() + "\nЗаказы:\n");
            guest.getOrderedServices().stream().sorted(comparator).forEach((service) -> out
                    .append(service.toString()).append("; Дата: ")
                    .append(service.getOrderTime()
                            .truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME))
                    .append("\n"));
            return out.toString();
        }
    }

    public static class GuestManager {
        private GuestManager(){}

        private final List<Guest> guests = new ArrayList<>();

        void loadGuestsDatabase() {
            Guest guest1 = new Guest("Ivanov Ivan Ivanovich", "1111 222222",
                    LocalDateTime.of(2022, 3, 2, 14, 0),
                    LocalDateTime.of(2022, 3, 5, 12, 0), null);
            Guest guest2 = new Guest("Ivanova Maria Ivanovna", "3333 444444",
                    LocalDateTime.of(2022, 3, 2, 14, 0),
                    LocalDateTime.of(2022, 3, 5, 12, 0), null);
            Guest guest3 = new Guest("Petrov Petr Petrovich", "5555 666666",
                    LocalDateTime.of(2022, 3, 1, 14, 0),
                    LocalDateTime.of(2022, 3, 14, 12, 0), null);
            Guest guest4 = new Guest("Abramov Nikita Alexandrovich", "7777 888888",
                    LocalDateTime.of(2022, 3, 1, 14, 0),
                    LocalDateTime.of(2022, 4, 1, 12, 0), null);
            Guest guest5 = new Guest("Yakovleva Margarita Vladimirovna", "9999 000000",
                    LocalDateTime.of(2022, 3, 1, 14, 0),
                    LocalDateTime.of(2022, 3, 14, 12, 0), null);
            guests.add(guest1);
            guests.add(guest2);
            guests.add(guest3);
            guests.add(guest4);
            guests.add(guest5);
        }

        List<Guest> getGuests() {
            return guests;
        }

        void addGuest(Guest guest) {
            if (guests.contains(guest)) {
                throw new KeyAlreadyExistsException("Guest already exists");
            }
            guests.add(guest);
        }

        void addGuestToRoom(Room room, Guest guest) {
            if (!guests.contains(guest)) {
                guests.add(guest);
            }
            guest.setRoom(room);
            room.addGuest(guest);
        }

        void removeGuest(Guest guest) {
            Room temp;
            try {
                temp = guest.getRoom();
            } catch (RuntimeException e) {
                guests.remove(guest); //if guest's room if null
                return;
            }
            temp.removeGuest(guest);
            guests.remove(guest);
        }

        String getGuestsAsString(List<Guest> subList) {
            StringBuilder out = new StringBuilder();
            subList.forEach((guest) -> out.append("Гость: ").append(guest.getFullName())
                    .append("; Номер: ").append(guest.getRoom().getRoomNumber())
                    .append("; Дата заезда: ").append(guest.getCheckInTime())
                    .append("; Дата освобождения: ").append(guest.getCheckOutTime()).append("\n"));
            return out.toString();
        }

        public List<Guest> sortGuests(List<Guest> roomsTableToSort, Comparator<Guest> comparator) {
            List<Guest> sorted = new ArrayList<>();
            roomsTableToSort.stream().sorted(comparator)
                    .forEach(sorted::add);
            return sorted;
        }

        public Guest getGuestByName(String fullName) throws NoSuchElementException {
            Guest result = guests.stream()
                    .filter(guest -> (fullName.equals(guest.getFullName())))
                    .findFirst().orElse(null);
            if (result == null) {
                throw new NoSuchElementException();
            }
            return result;
        }
    }
}















