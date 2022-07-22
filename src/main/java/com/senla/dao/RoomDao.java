package com.senla.dao;

import com.senla.model.Guest;
import com.senla.model.Room;
import com.senla.model.RoomStatus;

import java.time.LocalDate;
import java.util.List;

public interface RoomDao extends AbstractDao<Room> {

    List<Room> getFreeRoomsByDateSorted(LocalDate asAtSpecificDate, String fieldToSortBy);
    void removeGuest(Room room, Guest guest);
    void addGuestToRoom(Room room, Guest guest);
    void updateRoomStatus(Room room, RoomStatus roomStatus);
    void updateRoomPrice(Room room, int price);
    List<Guest> getGuestsOfRoom(Room room);
    Long getFreeRoomsAmount(LocalDate asAtSpecificDate);
}
