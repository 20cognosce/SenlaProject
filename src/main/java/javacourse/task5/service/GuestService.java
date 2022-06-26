package javacourse.task5.service;

import javacourse.task5.controller.action.SortEnum;
import javacourse.task5.dao.GuestDao;
import javacourse.task5.dao.entity.Guest;

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
}
