package com.senla.javacourse.service;

import com.senla.javacourse.controller.action.SortEnum;
import com.senla.javacourse.dao.GuestDao;
import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.Maintenance;

import java.time.LocalDate;
import java.util.List;

public interface GuestService extends AbstractService<Guest, GuestDao> {
    void createGuest(String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime, long roomId);
    void addGuestToRoom(long guestId, long roomId);
    void deleteGuest(long guestId);
    void removeGuestFromRoom(long guestId);
    List<Guest> getGuestsSorted(SortEnum sortBy);

    Long getAllAmount();
    Integer getPriceByGuest(long guestId);
    List<Guest> sortByAddition();
    List<Guest> sortByAlphabet();
    List<Guest> sortByCheckOutDate();

    List<Maintenance> getGuestMaintenanceList(long guestId);
}
