package task5.service;

import task5.dao.model.Guest;
import task5.dao.model.Room;
import task5.dao.model.RoomStatus;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public interface RoomService {
    void createRoom(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price);

    List<Room> getAll();
    String getAllAsString();
    String getAsString(List<Room> subList);
    Room getRoomById(int id) throws NoSuchElementException;
    List<Room> getFree();
    List<Room> getFree(LocalDate asAtSpecificDate);

    List<Room> getSorted(List<Room> roomsListToSort, Comparator<Room> comparator);
    List<Guest> getLastNGuests(int roomId, int N);
    String getGuestsListAsString(int roomId) throws NoSuchElementException;
}











