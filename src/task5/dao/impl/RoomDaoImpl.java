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
    public Room createRoom(int roomNumber, int guestsMaxNumber, int STARS_NUMBER, RoomStatus roomCurrentStatus, int price) {
        return new Room(idSupplier.supplyId(), roomNumber, guestsMaxNumber, STARS_NUMBER, roomCurrentStatus, price);
    }
    @Override
    public void addRoom(Room room) {
        repository.add(room);
    }

    @Override
    public List<Room> getAll() {
        return new ArrayList<>(repository);
    }

    @Override
    public String getAllAsString() {
        StringBuilder out = new StringBuilder();
        getAll().forEach(room -> out.append(room.toString()).append("\n"));
        return out.toString();
    }

    @Override
    public String getAllAsString(List<Room> subList) {
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
    public List<Room> getFree() {
        List<Room> freeRooms = new ArrayList<>();
        getAll().forEach((room) -> {
            if (room.getRoomCurrentStatus() == FREE) freeRooms.add(room);
        });
        return freeRooms;
    }

    @Override
    public List<Room> getFree(LocalDate asAtSpecificDate) {
        List<Room> freeRooms = new ArrayList<>();

        if (Objects.equals(asAtSpecificDate, LocalDate.now())) {
            return getFree();
        }

        getAll().forEach(room -> {
            boolean isFree = true;
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
