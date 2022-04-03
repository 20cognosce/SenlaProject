package task5.service;

import task5.dao.model.Guest;
import task5.dao.model.Room;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public interface GuestService {
    void createGuest(String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime, Room room);
    void addGuestToRoom(int guestId, int roomId);
    List<Guest> getAll();
    Guest getGuestById(int id) throws NoSuchElementException;
    Guest getGuestByName(String fullName) throws NoSuchElementException;
    String getAllAsString(List<Guest> subList);
    List<Guest> getSorted(List<Guest> listToSort, Comparator<Guest> comparator);

    int getAmount(List<Guest> subList);
    void deleteGuest(int guestId);
    void removeGuestFromRoom(int guestId);
}
