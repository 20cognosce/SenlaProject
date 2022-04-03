package task5.dao;

import task5.dao.model.Room;
import task5.dao.model.RoomStatus;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;


public interface RoomDao {
    Room createRoom(int roomNumber, int guestsMaxNumber, int STARS_NUMBER, RoomStatus roomCurrentStatus, int price);
    void addRoom(Room room);

    List<Room> getAll();
    String getAllAsString();
    String getAllAsString(List<Room> subList);
    Room getRoomById(int id) throws NoSuchElementException;
    List<Room> getFree();
    List<Room> getFree(LocalDate asAtSpecificDate);

    List<Room> getSorted(List<Room> roomsListToSort, Comparator<Room> comparator);
}
