package task5.service.impl;

import task5.controller.action.SortEnum;
import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.model.Guest;
import task5.dao.model.Room;
import task5.service.GuestService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;


public class GuestServiceImpl extends AbstractServiceImpl<Guest, GuestDao> implements GuestService {
    public GuestServiceImpl (GuestDao guestDao, RoomDao roomDao, MaintenanceDao maintenanceDao) {
        super(guestDao, guestDao, roomDao, maintenanceDao);
    }

    @Override
    public void createGuest(String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime, int roomId) {
        if (roomId == 0) {
            guestDao.createGuest(fullName, passport, checkInTime, checkOutTime, null);
            return;
        }

        try {
            guestDao.createGuest(fullName, passport, checkInTime, checkOutTime, roomDao.getById(roomId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addGuestToRoom(int guestId, int roomId) {
        Guest guest;
        Room room;
        try {
            guest = guestDao.getById(guestId);
            room = roomDao.getById(roomId);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        guestDao.addGuestToRoom(guest, room);
    }

    @Override
    public void deleteGuest(int guestId) {
        try {
            guestDao.deleteGuest(guestDao.getById(guestId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeGuestFromRoom(int guestId) throws NoSuchElementException {
        guestDao.removeGuestFromRoom(guestDao.getById(guestId));
    }

    @Override
    public int getAmount(List<Guest> subList) {
        return subList.size();
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
}
