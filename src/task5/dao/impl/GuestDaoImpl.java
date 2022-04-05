package task5.dao.impl;

import task5.dao.GuestDao;
import task5.dao.model.Guest;
import task5.dao.model.IdSupplier;
import task5.dao.model.Room;
import task5.dao.model.RoomStatus;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDate;
import java.util.*;

public class GuestDaoImpl extends AbstractDaoImpl<Guest> implements GuestDao {
    private final IdSupplier idSupplier = new IdSupplier();

    public GuestDaoImpl() {
        super();
    }

    @Override
    public void createGuest(String fullName, String passport,
                            LocalDate checkInTime, LocalDate checkOutTime,
                            Room room) throws KeyAlreadyExistsException {
        Guest newGuest = new Guest(idSupplier.supplyId(), fullName, passport, checkInTime, checkOutTime, room);
        addToRepo(newGuest);
    }

    @Override
    public void addGuestToRoom(Guest guest, Room room) throws RuntimeException {
        if (room.getRoomStatus() == RoomStatus.UNDER_REPAIR || room.getRoomStatus() == RoomStatus.CLEANING) {
            throw new RuntimeException("Inappropriate room status");
        }
        if (room.getCapacity() <= room.getGuestsCurrentList().size()) {
            throw new RuntimeException("Room's capacity limit is exceeded");
        }
        room.addGuest(guest);
        guest.setRoom(room);
        updatePrice(guest);
    }

    @Override
    public void removeGuestFromRoom(Guest guest) throws NullPointerException, NoSuchElementException {
        guest.getRoom().removeGuest(guest);
        guest.setRoom(null);
    }

    @Override
    public void updatePrice(Guest guest) {
        Room room = guest.getRoom();
        //pay only the first settled after the room was empty
        if (!Objects.isNull(room) && room.getGuestsCurrentList().size() == 1) {
            guest.setPrice(guest.getPrice() + room.getPrice());
        }
    }

    @Override
    public void deleteGuest(Guest guest) throws NullPointerException, NoSuchElementException {
        removeGuestFromRoom(guest);
        deleteFromRepo(guest);
    }
}
