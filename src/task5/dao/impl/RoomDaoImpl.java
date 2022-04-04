package task5.dao.impl;

import task5.dao.RoomDao;
import task5.dao.model.Guest;
import task5.dao.model.IdSupplier;
import task5.dao.model.Room;
import task5.dao.model.RoomStatus;

import java.time.LocalDate;
import java.util.*;

import static task5.dao.model.RoomStatus.FREE;

public class RoomDaoImpl implements RoomDao {
    private final IdSupplier idSupplier = new IdSupplier();
    private final List<Room> repository = new ArrayList<>();

    @Override
    public void createRoom(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price) {
        repository.add(new Room(idSupplier.supplyId(), name, capacity, starsNumber, roomStatus, price));
    }

    @Override
    public List<Room> getAll() {
        return new ArrayList<>(repository);
    }

    @Override
    public String getAllAsString() {
        StringBuilder out = new StringBuilder();
        getAll().forEach(room -> out.append(room.toString()));
        return out.toString();
    }

    @Override
    public String getAsString(List<Room> subList) {
        StringBuilder out = new StringBuilder();
        subList.forEach(room -> out.append(room.toString()));
        return out.toString();
    }
    @Override
    public Room getRoomById(int id) throws NoSuchElementException {
        return getAll().stream()
                .filter(room -> (room.getId() == id))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Room> getSorted(List<Room> roomsListToSort, Comparator<Room> comparator) {
        List<Room> sorted = new ArrayList<>();
        roomsListToSort.stream().sorted(comparator)
                .forEach(sorted::add);
        return sorted;
    }

    @Override
    public String getGuestsListAsString(int roomId) throws NoSuchElementException {
        Room temp = getRoomById(roomId);
        return temp.getGuestsListAsString(temp.getGuestsCurrentList());
    }

    @Override
    public List<Room> getFree() {
        List<Room> freeRooms = new ArrayList<>();
        getAll().forEach((room) -> {
            if (room.getRoomStatus() == FREE) freeRooms.add(room);
        });
        return freeRooms;
    }

    @Override
    public List<Room> getFree(LocalDate asAtSpecificDate) {
        if (Objects.equals(asAtSpecificDate, LocalDate.now())) {
            return getFree();
        }

        List<Room> freeRooms = new ArrayList<>();
        getAll().forEach(room -> {
            boolean isFree = true;

            if (room.getGuestsCurrentList().isEmpty()) {
                if (room.getRoomStatus() == FREE) {
                    freeRooms.add(room);
                }
                return;
            }

            for (Guest guest : room.getGuestsCurrentList()) {
                if (!guest.getCheckInDate().isAfter(asAtSpecificDate) &&
                        guest.getCheckOutDate().isAfter(asAtSpecificDate)) isFree = false;
            }
            if (isFree) {
                freeRooms.add(room);
            }
        });

        return freeRooms;
    }
}
