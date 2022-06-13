package javacourse.task5.dao.impl;

import javacourse.task5.build.factory.Component;
import javacourse.task5.dao.GuestDao;
import javacourse.task5.dao.RoomDao;
import javacourse.task5.dao.entity.Guest;
import javacourse.task5.dao.entity.Room;
import javacourse.task5.dao.entity.RoomStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RoomDaoImpl extends AbstractDaoImpl<Room> implements RoomDao {
    public RoomDaoImpl() {
        super();
    }

    @Override
    public List<Room> getFree() {
        List<Room> freeRooms = new ArrayList<>();
        getAll().forEach((room) -> {
            if (room.getRoomStatus() == RoomStatus.FREE) freeRooms.add(room);
        });
        return freeRooms;
    }

    @Override
    public List<Room> getFree(LocalDate asAtSpecificDate, GuestDao guestDao) {
        if (Objects.equals(asAtSpecificDate, LocalDate.now())) {
            return getFree();
        }

        List<Room> freeRooms = new ArrayList<>();
        getAll().forEach(room -> {
            final boolean[] isFree = {true};

            if (room.getCurrentGuestIdList().isEmpty()) {
                if (room.getRoomStatus() == RoomStatus.FREE) {
                    freeRooms.add(room);
                }
                return;
            }

            room.getCurrentGuestIdList().forEach(guestId -> {
                Guest guest = guestDao.getById(guestId);
                if (!guest.getCheckInDate().isAfter(asAtSpecificDate) &&
                        guest.getCheckOutDate().isAfter(asAtSpecificDate)) isFree[0] = false;
            });
            if (isFree[0]) {
                freeRooms.add(room);
            }
        });

        return freeRooms;
    }

    @Override
    public void setStatus(long roomId, RoomStatus roomStatus) {
        getById(roomId).setRoomStatus(roomStatus);
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

    @Override
    public Room getDaoEntity() {
        return new Room();
    }
}