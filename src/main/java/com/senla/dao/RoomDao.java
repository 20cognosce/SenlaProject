package com.senla.dao;

import com.senla.model.Guest;
import com.senla.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomDao extends AbstractDao<Room> {

    List<Room> getFreeRoomsByDateSorted(LocalDate asAtSpecificDate, String fieldToSortBy, String order);
    void removeGuest(Room room, Guest guest);
    void addGuestToRoom(Room room, Guest guest);
    Long getFreeRoomsAmount(LocalDate asAtSpecificDate);
}
