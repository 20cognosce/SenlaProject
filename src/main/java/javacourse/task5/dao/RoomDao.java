package javacourse.task5.dao;

import javacourse.task5.dao.entity.Guest;
import javacourse.task5.dao.entity.Room;
import javacourse.task5.dao.entity.RoomStatus;

import java.time.LocalDate;
import java.util.List;

public interface RoomDao extends AbstractDao<Room> {
    List<Room> getFree();
    List<Room> getFree(LocalDate asAtSpecificDate, String fieldNameToSortBy);
    void removeGuest(Room room, Guest guest);
    void addGuestToRoom(Room room, Guest guest);
    void updateRoomStatus(Room room, RoomStatus roomStatus);
    void updateRoomPrice(Room room, int price);
}
