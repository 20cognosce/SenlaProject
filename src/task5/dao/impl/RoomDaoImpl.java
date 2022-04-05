package task5.dao.impl;

import task5.dao.RoomDao;
import task5.dao.model.Guest;
import task5.dao.model.IdSupplier;
import task5.dao.model.Room;
import task5.dao.model.RoomStatus;

import java.time.LocalDate;
import java.util.*;

import static task5.dao.model.RoomStatus.FREE;

public class RoomDaoImpl extends AbstractDaoImpl<Room> implements RoomDao {
    private final IdSupplier idSupplier = new IdSupplier();

    public RoomDaoImpl() {
        super();
    }

    @Override
    public void createRoom(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price) {
        addToRepo(new Room(idSupplier.supplyId(), name, capacity, starsNumber, roomStatus, price));
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
