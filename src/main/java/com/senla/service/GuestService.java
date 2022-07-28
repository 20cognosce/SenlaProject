package com.senla.service;

import com.senla.dao.GuestDao;
import com.senla.model.Guest;
import com.senla.model.Maintenance;

import java.util.List;

public interface GuestService extends AbstractService<Guest, GuestDao> {

    void createGuest(Guest guest);
    void addGuestToRoom(long guestId, long roomId);
    void deleteGuest(long guestId);
    void removeGuestFromRoom(long guestId);

    Long getAllAmount();
    Integer getPriceByGuest(long guestId);
    List<Guest> sortByAddition(String order);
    List<Guest> sortByAlphabet(String order);
    List<Guest> sortByCheckOutDate(String order);

    List<Maintenance> getGuestMaintenanceList(long guestId);
}
