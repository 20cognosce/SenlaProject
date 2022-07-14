package com.senla.javacourse.dao;

import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.Room;
import com.senla.javacourse.dao.entity.RoomStatus;

import java.time.LocalDate;
import java.util.List;

public interface RoomDao extends AbstractDao<Room> {
    List<Room> getFreeRoomsNowSorted(String fieldToSortBy);
    List<Room> getFreeRoomsByDateSorted(LocalDate asAtSpecificDate, String fieldToSortBy);
    void removeGuest(Room room, Guest guest);
    void addGuestToRoom(Room room, Guest guest);
    void updateRoomStatus(Room room, RoomStatus roomStatus);
    void updateRoomPrice(Room room, int price);
    List<Guest> getGuestsOfRoom(Room room);
}
