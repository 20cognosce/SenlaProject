package task5.dao;

import task5.dao.model.Room;
import task5.dao.model.RoomStatus;

import java.time.LocalDate;
import java.util.List;

public interface RoomDao extends AbstractDao<Room> {
    void createRoom(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price);
    List<Room> getFree();
    List<Room> getFree(LocalDate asAtSpecificDate);
}
