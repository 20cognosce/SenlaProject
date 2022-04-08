package task5.service;

import task5.controller.action.SortEnum;
import task5.dao.model.Guest;

import java.time.LocalDate;
import java.util.List;

public interface GuestService extends AbstractService<Guest> {
    void createGuest(String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime, long roomId);
    void createGuest(long guestId, String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime, long roomId);
    void addGuestToRoom(long guestId, long roomId);
    void deleteGuest(long guestId);
    void removeGuestFromRoom(long guestId);
    List<Guest> getSorted(List<Guest> listToSort, SortEnum sortBy);

    Integer getAllAmount();
    Integer getPriceByGuest(long guestId);
    List<Guest> sortByAddition();
    List<Guest> sortByAlphabet();
    List<Guest> sortByCheckOutDate();
}
