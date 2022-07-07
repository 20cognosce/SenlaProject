package com.senla.javacourse.service.impl;

import com.senla.javacourse.controller.action.SortEnum;
import com.senla.javacourse.dao.GuestDao;
import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.Room;
import com.senla.javacourse.service.GuestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InvalidAttributeValueException;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class GuestServiceImpl extends AbstractServiceImpl<Guest, GuestDao> implements GuestService {
    public GuestServiceImpl() {
        super();
    }

    @Override
    @Transactional
    public void createGuest(String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime,
                            long roomId) throws KeyAlreadyExistsException {
        Room room;
        if (roomId == 0) {
            room = null;
        } else {
            room = roomDao.getById(roomId);
        }
        Guest guest = new Guest(fullName, 0, passport, checkInTime, checkOutTime, room, new ArrayList<>());
        guestDao.create(guest);
        if (!Objects.isNull(room)) guestDao.updateGuestPrice(guest, room.getPrice());
    }


    @Override
    @Transactional
    public void addGuestToRoom(long guestId, long roomId) {
        Guest guest = guestDao.getById(guestId);
        Room room = roomDao.getById(roomId);
        if (room.isUnavailableToSettle()) throw new RuntimeException("Room is unavailable");
        if (!Objects.isNull(guest.getRoom())) removeGuestFromRoom(guestId);
        roomDao.addGuestToRoom(room, guest);
        guestDao.updateGuestRoom(guest, room);

        Room roomUpdated = roomDao.getById(roomId);
        if (roomUpdated.getCurrentGuestList().size() == 1) {
            //pay only the first settled after the room was empty
            guestDao.updateGuestPrice(guest, roomUpdated.getPrice());
        }
    }

    @Override
    @Transactional
    public void removeGuestFromRoom(long guestId) {
        Guest guest;
        guest = guestDao.getById(guestId);
        if (Objects.isNull(guest.getRoom())) {
            return;
        }
        roomDao.removeGuest(guest.getRoom(), guest);
        guestDao.updateGuestRoom(guest, null);
        guestDao.updateGuestPrice(guest, 0);
    }

    @Override
    @Transactional
    public void deleteGuest(long guestId) {
        removeGuestFromRoom(guestId);
        guestDao.delete(guestDao.getById(guestId));
    }

    @Override
    public List<Guest> getGuestsSorted(SortEnum sortEnum) {
        String fieldToSort;

        switch (sortEnum) {
            case BY_ADDITION:fieldToSort = "id"; break;
            case BY_PRICE: fieldToSort = "price"; break;
            case BY_ALPHABET:  fieldToSort = "name"; break;
            case BY_CHECKOUT_DATE:fieldToSort = "checkOutDate"; break;
            default: throw new NoSuchElementException();
        }

        return getDefaultDao().getAll(fieldToSort);
    }

    @Override
    public Long getAllAmount() {
        return getDefaultDao().getAllAmount();
    }

    @Override
    public Integer getPriceByGuest(long guestId) {
        return getById(guestId).getPrice();
    }

    @Override
    public List<Guest> sortByAddition() {
        return getGuestsSorted(SortEnum.BY_ADDITION);
    }

    @Override
    public List<Guest> sortByAlphabet() {
        return getGuestsSorted(SortEnum.BY_ALPHABET);
    }

    @Override
    public List<Guest> sortByCheckOutDate() {
        return getGuestsSorted(SortEnum.BY_CHECKOUT_DATE);
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
        return getDefaultDao().exportData(getById(id));
    }

    @Override
    public GuestDao getDefaultDao() {
        return guestDao;
    }
}
