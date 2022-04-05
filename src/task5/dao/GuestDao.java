package task5.dao;

import task5.dao.model.Guest;
import task5.dao.model.Room;

import java.time.LocalDate;

public interface GuestDao extends AbstractDao<Guest> {
    void createGuest(String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime, Room room);
    void deleteGuest(Guest guest);
    void addGuestToRoom(Guest guest, Room room);
    void removeGuestFromRoom(Guest guest);
    void updatePrice(Guest guest);
}
