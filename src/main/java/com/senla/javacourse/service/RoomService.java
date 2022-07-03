package com.senla.javacourse.service;

import com.senla.javacourse.controller.action.SortEnum;
import com.senla.javacourse.dao.entity.Room;
import com.senla.javacourse.dao.RoomDao;
import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.RoomStatus;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public interface RoomService extends AbstractService<Room, RoomDao> {
    void createRoom(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price);
    List<Room> getFree();
    List<Room> getFree(LocalDate asAtSpecificDate);
    List<Guest> getLastNGuests(long roomId);
    List<Guest> getGuestsList(long roomId) throws NoSuchElementException;
    List<Room> getRoomsSorted(SortEnum sortBy);

    void updateRoomPrice(long roomId, int price);
    void updateRoomStatus(long roomId, RoomStatus roomStatus) throws ServiceUnavailableException;

    List<Room> sortByAddition();
    List<Room> sortByCapacity();
    List<Room> sortByPrice();
    List<Room> sortByStars();

    List<Room> sortFreeRoomsByAddition(LocalDate specificDate);
    List<Room> sortFreeRoomsByCapacity(LocalDate specificDate);
    List<Room> sortFreeRoomsByPrice(LocalDate specificDate);
    List<Room> sortFreeRoomsByStars(LocalDate specificDate);
}











