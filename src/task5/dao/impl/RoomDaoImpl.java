package task5.dao.impl;

import task5.dao.RoomDao;
import task5.dao.entity.Guest;
import task5.dao.entity.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static task5.dao.entity.RoomStatus.FREE;

public class RoomDaoImpl extends AbstractDaoImpl<Room> implements RoomDao {
    public RoomDaoImpl() {
        super();
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

    @Override
    public String exportData(Room room) {
        return room.getId() + "," +
                room.getName() + "," +
                room.getCapacity() + "," +
                room.getStarsNumber() + "," +
                room.getRoomStatus() + "," +
                room.getPrice();
    }
}
