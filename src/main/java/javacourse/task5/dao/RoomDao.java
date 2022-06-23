package javacourse.task5.dao;

import javacourse.task5.dao.entity.Room;
import javacourse.task5.dao.entity.RoomStatus;

import java.time.LocalDate;
import java.util.List;

public interface RoomDao extends AbstractDao<Room> {
    List<Room> getFree();
    List<Room> getFree(LocalDate asAtSpecificDate, GuestDao guestDao);
    void removeGuest(long roomId, long guestId);
    void addGuestToRoom(long roomId, long guestId);
    void updateRoomStatus(long roomId, RoomStatus roomStatus);
    void updateRoomPrice(long roomId, int price);
}
