package task5.service.impl;

import task5.controller.action.SortEnum;
import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.entity.Guest;
import task5.dao.entity.Room;
import task5.service.GuestService;

import javax.management.InvalidAttributeValueException;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                            long roomId) throws KeyAlreadyExistsException {
        try {
            if (roomId != 0) roomDao.getById(roomId);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return;
        }
        guestDao.synchronizeNextSuppliedId(getAll().get(getAll().size() - 1).getId());
        long suppliedId = guestDao.supplyId();
        guestDao.addToRepo(new Guest(suppliedId, fullName, passport, checkInTime, checkOutTime, roomId));
        if (roomId != 0) guestDao.updatePrice(guestDao.getById(suppliedId), roomDao.getById(roomId));
    }

    @Override
    public void addGuestToRoom(long guestId, long roomId) {
        Guest guest;
        Room room;
        try {
            guest = guestDao.getById(guestId);
            room = roomDao.getById(roomId);
            if (room.isUnavailableToSettle()) throw new RuntimeException("Room is unavailable");
            if (guest.getRoomId() != 0) removeGuestFromRoom(guestId);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        room.addGuest(guest);
        guest.setRoomId(room.getId());
        guestDao.updatePrice(guest, room);
    }

    @Override
    public void removeGuestFromRoom(long guestId) {
        Guest guest;
        try {
            guest = guestDao.getById(guestId);

            guestDao.addToArchivedRepository(guestDao.getById(guestId));
            roomDao.getById(guest.getRoomId()).removeGuest(guestId);
            guest.setRoomId(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGuest(long guestId) {
        try {
            removeGuestFromRoom(guestId);
            guestDao.addToArchivedRepository(guestDao.getById(guestId));
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

    @Override
    public void addAllArchived(List<Guest> list) {
        list.forEach(guest -> {
            try {
                guestDao.addToArchivedRepository(guest);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public List<Guest> getArchivedAll() {
        return guestDao.getArchivedAll();
    }

    @Override
    public void importData(List<List<String>> records) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        records.forEach(entry -> {
            long guestId;
            String name;
            String passport;
            LocalDate checkInDate;
            LocalDate checkOutDate;
            long roomId;

            try {
                guestId = Long.parseLong(entry.get(0));
                name = entry.get(1);
                passport = entry.get(2);
                checkInDate = LocalDate.parse(entry.get(3), dtf);
                checkOutDate = LocalDate.parse(entry.get(4), dtf);
                roomId = Long.parseLong(entry.get(5));
            } catch (Exception e) {
                System.out.println(e.getClass().getCanonicalName() + ": "  + e.getMessage());
                return;
            }

            //If room's check fails the entire item fails
            if (roomId != 0) {
                try {
                    if (roomDao.getById(roomId).isUnavailableToSettle())
                        throw new InvalidAttributeValueException("Room is unavailable");
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }

            try {
                Guest guest = getById(guestId);
                guest.setName(name);
                guest.setPassport(passport);
                guest.setCheckInDate(checkInDate);
                guest.setCheckOutDate(checkOutDate);
                if (roomId == 0) {
                    removeGuestFromRoom(guestId);
                } else {
                    addGuestToRoom(guestId, roomId);
                }
            } catch (NoSuchElementException e) {
                guestDao.synchronizeNextSuppliedId(guestId);
                createGuest(name, passport, checkInDate, checkOutDate, 0);
                if (roomId != 0) addGuestToRoom(guestId, roomId);
            }
        });
    }

    @Override
    public String getExportTitleLine() {
        return "id,Name,Passport,CheckInDate [dd.MM.yyyy],CheckOutDate [dd.MM.yyyy],roomId";
    }

    @Override
    public String exportData(long id) throws NoSuchElementException {
        return currentDao.exportData(getById(id));
    }
}
