package task5.service;

import task5.controller.action.SortEnum;
import task5.dao.entity.Guest;
import task5.dao.entity.Room;
import task5.dao.entity.RoomStatus;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public interface RoomService extends AbstractService<Room> {
    void createRoom(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price);
    List<Room> getFree();
    List<Room> getFree(LocalDate asAtSpecificDate);
    List<Guest> getLastNGuests(long roomId);
    List<Guest> getGuestsList(long roomId) throws NoSuchElementException;
    List<Room> getSorted(List<Room> listToSort, SortEnum sortBy);

    void setPrice(long roomId, int price);
    void setStatus(long roomId, RoomStatus roomStatus) throws ServiceUnavailableException;

    List<Room> sortByAddition();
    List<Room> sortByCapacity();
    List<Room> sortByPrice();
    List<Room> sortByStars();

    List<Room> sortFreeRoomsByAddition(LocalDate specificDate);
    List<Room> sortFreeRoomsByCapacity(LocalDate specificDate);
    List<Room> sortFreeRoomsByPrice(LocalDate specificDate);
    List<Room> sortFreeRoomsByStars(LocalDate specificDate);
}











