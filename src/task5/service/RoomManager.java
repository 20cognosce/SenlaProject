package task5.service;

import task5.dao.Guest;
import task5.dao.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static task5.dao.RoomStatus.*;
import static task5.dao.RoomStatus.FREE;

public class RoomManager {
    RoomManager(){}

    private final List<Room> rooms = new ArrayList<>();

    public void loadRoomsDatabase() {
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

    public void addNewRoom(Room room) {
        rooms.add(room);
    }

    public Room getRoomByNumber(Integer roomNumber) throws NoSuchElementException {
        Room result = rooms.stream()
                .filter(room -> (roomNumber.equals(room.getRoomNumber())))
                .findFirst().orElse(null);
        if (result == null) {
            throw new NoSuchElementException();
        }
        return result;
    }

    public String getRoomsAsString() {
        StringBuilder out = new StringBuilder();
        rooms.forEach(room -> out.append(room.toString()));
        return out.toString();
    }

    public String getRoomsAsString(List<Room> subList) {
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

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Room> getFreeRooms() {
        List<Room> freeRooms = new ArrayList<>();
        rooms.forEach((room) -> {
            if (room.getRoomCurrentStatus() == FREE) freeRooms.add(room);
        });
        return freeRooms;
    }

    public List<Room> getFreeRooms(LocalDate asAtSpecificDate) {
        List<Room> freeRooms = new ArrayList<>();
        List<Room> rooms = getRooms();

        rooms.forEach(room -> {
            boolean isFree = true;
            for (Guest guest : room.getGuestsCurrentList()) {
                if (!guest.getCheckInDate().isAfter(asAtSpecificDate) &&
                        guest.getCheckOutDate().isAfter(asAtSpecificDate)) isFree = false;
            }
            if (isFree) freeRooms.add(room);
        });

        return freeRooms;
    }
}
