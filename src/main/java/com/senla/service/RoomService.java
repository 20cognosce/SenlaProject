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
    Long getFreeAmount(LocalDate asAtSpecificDate);
    List<Guest> getLastNGuests(long roomId);
    List<Guest> getGuestsList(long roomId) throws NoSuchElementException;

    void updateRoom(Room room) throws ServiceUnavailableException;
    void updateRoomDetails(long roomId, String details);

    List<Room> getFree(LocalDate asAtSpecificDate, String order);
    List<Room> sortByAddition(String order);
    List<Room> sortByCapacity(String order);
    List<Room> sortByPrice(String order);
    List<Room> sortByStars(String order);

    List<Room> sortFreeRoomsByAddition(LocalDate specificDate, String order);
    List<Room> sortFreeRoomsByCapacity(LocalDate specificDate, String order);
    List<Room> sortFreeRoomsByPrice(LocalDate specificDate, String order);
    List<Room> sortFreeRoomsByStars(LocalDate specificDate, String order);
}











