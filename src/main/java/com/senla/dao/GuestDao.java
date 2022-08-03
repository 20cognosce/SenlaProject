package com.senla.dao;

import com.senla.model.Guest;
import com.senla.model.Room;

public interface GuestDao extends AbstractDao<Guest> {

    void updateGuestPrice(Guest guest, int price);
    void updateGuestRoom(Guest guest, Room room);
    long getAllAmount();
    Guest getGuestByLogin(String login);
}
