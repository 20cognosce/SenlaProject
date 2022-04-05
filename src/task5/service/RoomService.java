package task5.service;

import task5.controller.action.SortEnum;
import task5.dao.model.Guest;
import task5.dao.model.Room;
import task5.dao.model.RoomStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public interface RoomService extends AbstractService<Room> {
    void createRoom(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price);
    List<Room> getFree();
    List<Room> getFree(LocalDate asAtSpecificDate);
    List<Guest> getLastNGuests(int roomId, int N);
    List<Guest> getGuestsList(int roomId) throws NoSuchElementException;
    List<Room> getSorted(List<Room> listToSort, SortEnum sortBy);

    void setPrice(int roomId, int price);
    void setStatus(int roomId, RoomStatus roomStatus);

}











