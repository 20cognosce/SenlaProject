package com.senla.javacourse.dao;

import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.Room;

public interface GuestDao extends AbstractDao<Guest> {
    void updateGuestPrice(Guest guest, int price);
    void updateGuestRoom(Guest guest, Room room);
}
