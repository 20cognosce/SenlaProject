package task5.dao;

import task5.dao.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomDao extends AbstractDao<Room> {
    List<Room> getFree();
    List<Room> getFree(LocalDate asAtSpecificDate);
}
