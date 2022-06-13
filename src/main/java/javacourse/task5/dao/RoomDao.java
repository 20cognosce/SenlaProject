package javacourse.task5.dao;

import javacourse.task5.dao.entity.Room;
import javacourse.task5.dao.entity.RoomStatus;

import java.time.LocalDate;
import java.util.List;

public interface RoomDao extends AbstractDao<Room> {
    List<Room> getFree();
    List<Room> getFree(LocalDate asAtSpecificDate, GuestDao guestDao);
    void setStatus(long roomId, RoomStatus roomStatus);
}
