package task5.service;

import task5.controller.action.SortEnum;
import task5.dao.model.Guest;

import java.time.LocalDate;
import java.util.List;

public interface GuestService extends AbstractService<Guest> {
    void createGuest(String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime, int roomId);
    void addGuestToRoom(int guestId, int roomId);
    int getAmount(List<Guest> subList);
    void deleteGuest(int guestId);
    void removeGuestFromRoom(int guestId);
    List<Guest> getSorted(List<Guest> listToSort, SortEnum sortBy);
}
