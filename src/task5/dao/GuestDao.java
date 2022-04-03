package task5.dao;

import task5.dao.model.Guest;
import task5.dao.model.Room;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public interface GuestDao {
    void createGuest(String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime, Room room);
    void addGuestToRoom(Guest guest, Room room);

    List<Guest> getAll();
    Guest getGuestById(int id) throws NoSuchElementException;
    Guest getGuestByName(String fullName) throws NoSuchElementException;
    String getAllAsString(List<Guest> subList);

    void deleteGuest(Guest guest);

    List<Guest> getSorted(List<Guest> listToSort, Comparator<Guest> comparator);
}
