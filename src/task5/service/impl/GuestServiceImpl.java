package task5.service.impl;

import task5.controller.action.SortEnum;
import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.model.Guest;
import task5.dao.model.Room;
import task5.dao.model.RoomStatus;
import task5.service.GuestService;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;


public class GuestServiceImpl extends AbstractServiceImpl<Guest, GuestDao> implements GuestService {
    public GuestServiceImpl (GuestDao guestDao, RoomDao roomDao, MaintenanceDao maintenanceDao) {
        super(guestDao, guestDao, roomDao, maintenanceDao);
    }

    @Override
    public void createGuest(String fullName, String passport,
                            LocalDate checkInTime, LocalDate checkOutTime,
                            long roomId) throws KeyAlreadyExistsException, NoSuchElementException {
        if (roomId == 0) {
            guestDao.addToRepo(
                    new Guest(guestDao.supplyId(), fullName, passport, checkInTime, checkOutTime, null));
        } else {
            guestDao.addToRepo(
                    new Guest(guestDao.supplyId(), fullName, passport, checkInTime, checkOutTime, roomDao.getById(roomId)));
        }
    }

    @Override
    public void addGuestToRoom(long guestId, long roomId) {
        Guest guest;
        Room room;
        try {
            guest = guestDao.getById(guestId);
            room = roomDao.getById(roomId);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (room.getRoomStatus() == RoomStatus.UNDER_REPAIR || room.getRoomStatus() == RoomStatus.CLEANING) {
            throw new RuntimeException("Inappropriate room status");
        }
        if (room.getCapacity() <= room.getGuestsCurrentList().size()) {
            throw new RuntimeException("Room's capacity limit is exceeded");
        }
        room.addGuest(guest);
        guest.setRoom(room);
        guestDao.updatePrice(guest);
    }

    @Override
    public void removeGuestFromRoom(long guestId) {
        Guest guest;
        try {
            guest = guestDao.getById(guestId);
            try {
                guest.getRoom().removeGuest(guest);
                guest.setRoom(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGuest(long guestId) {
        try {
            removeGuestFromRoom(guestId);
            guestDao.deleteFromRepo(guestDao.getById(guestId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Guest> getSorted(List<Guest> listToSort, SortEnum sortBy) throws NoSuchElementException {
        switch (sortBy) {
            case BY_ADDITION: return currentDao.getSorted(listToSort, Comparator.comparingLong(Guest::getId));
            case BY_PRICE: return currentDao.getSorted(listToSort, Comparator.comparingInt(Guest::getPrice));
            case BY_ALPHABET: return currentDao.getSorted(listToSort, Comparator.comparing(Guest::getName));
            case BY_CHECKOUT_DATE: return currentDao.getSorted(listToSort, Comparator.comparing(Guest::getCheckOutDate));
        }
        throw new NoSuchElementException();
    }

    @Override
    public Integer getAllAmount() {
        return guestDao.getAll().size();
    }

    @Override
    public Integer getPriceByGuest(long guestId) {
        return getById(guestId).getPrice();
    }

    @Override
    public List<Guest> sortByAddition() {
        return getSorted(getAll(), SortEnum.BY_ADDITION);
    }

    @Override
    public List<Guest> sortByAlphabet() {
        return getSorted(getAll(), SortEnum.BY_ALPHABET);
    }

    @Override
    public List<Guest> sortByCheckOutDate() {
        return getSorted(getAll(), SortEnum.BY_CHECKOUT_DATE);
    }
}
