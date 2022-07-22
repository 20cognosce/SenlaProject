package com.senla.service;

import com.senla.dao.RoomDao;
import com.senla.model.Guest;
import com.senla.model.Room;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public interface RoomService extends AbstractService<Room, RoomDao> {

    void createRoom(Room room);
    List<Room> getFree(LocalDate asAtSpecificDate);
    Long getFreeAmount(LocalDate asAtSpecificDate);
    List<Guest> getLastNGuests(long roomId);
    List<Guest> getGuestsList(long roomId) throws NoSuchElementException;

    void updateRoom(Room room) throws ServiceUnavailableException;
    void updateRoomDetails(long roomId, String details);

    List<Room> sortByAddition();
    List<Room> sortByCapacity();
    List<Room> sortByPrice();
    List<Room> sortByStars();

    List<Room> sortFreeRoomsByAddition(LocalDate specificDate);
    List<Room> sortFreeRoomsByCapacity(LocalDate specificDate);
    List<Room> sortFreeRoomsByPrice(LocalDate specificDate);
    List<Room> sortFreeRoomsByStars(LocalDate specificDate);
}











