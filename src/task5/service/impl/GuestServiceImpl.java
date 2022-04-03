package task5.service.impl;

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


public class GuestServiceImpl extends AbstractServiceImpl implements GuestService {
    public GuestServiceImpl (GuestDao guestDao, RoomDao roomDao, MaintenanceDao maintenanceDao) {
        super(guestDao, roomDao, maintenanceDao);
    }

    @Override
    public int getAmount(List<Guest> subList) {
        return subList.size();
    }

    @Override
    public void deleteGuest(int guestId) {
        try {
            guestDao.deleteGuest(guestDao.getGuestById(guestId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createGuest(String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime, Room room) {
        guestDao.createGuest(fullName, passport, checkInTime, checkOutTime, room);
    }


    @Override
    public void addGuestToRoom(int guestId, int roomId) {
        Guest guest;
        Room room;
        try {
            guest = guestDao.getGuestById(guestId);
            room = roomDao.getRoomById(roomId);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        guestDao.addGuestToRoom(guest, room);
    }

    @Override
    public List<Guest> getAll() {
        return guestDao.getAll();
    }

    @Override
    public Guest getGuestById(int id) throws NoSuchElementException {
        return guestDao.getGuestById(id);
    }

    @Override
    public Guest getGuestByName(String fullName) throws NoSuchElementException {
        return guestDao.getGuestByName(fullName);
    }

    @Override
    public String getAllAsString(List<Guest> subList) {
       return guestDao.getAllAsString(subList);
    }

    @Override
    public void removeGuestFromRoom(int guestId) throws NoSuchElementException {
        //TODO: убрать и из комнаты
        guestDao.getGuestById(guestId).setRoom(null);
    }

    @Override
    public List<Guest> getSorted(List<Guest> listToSort, Comparator<Guest> comparator) {
        return guestDao.getSorted(listToSort, comparator);
    }
}
