package com.senla.service.impl;

import com.senla.dao.GuestDao;
import com.senla.dao.RoomDao;
import com.senla.model.Guest;
import com.senla.model.Room;
import com.senla.service.GuestService;
import com.senla.util.SortEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl extends AbstractServiceImpl<Guest, GuestDao> implements GuestService {

    private final GuestDao guestDao;
    private final RoomDao roomDao;

    @Override
    @Transactional
    public void createGuest(Guest guest) {
        if (Objects.isNull(guest.getRoom())) {
            guestDao.create(guest);
        } else {
            throw new IllegalArgumentException("Creating only a guest without room is possible");
        }
    }

    @Override
    @Transactional
    public void addGuestToRoom(long guestId, long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        Guest guest = guestDao.getById(guestId);
        Room room = roomDao.getById(roomId);
        if (!room.isAvailableToSettle()) {
            throw new IllegalArgumentException("Room is unavailable");
        }
        if (!Objects.isNull(guest.getRoom())) {
            throw new IllegalArgumentException("Guest has the room already. Remove it first");
        }
        if (!checkOutDate.isAfter(checkInDate)) {
            throw new IllegalArgumentException("Incorrect settlement dates");
        }
        roomDao.addGuestToRoom(room, guest);
        guestDao.updateGuestRoom(guest, room);
        guest.setCheckInDate(checkInDate);
        guest.setCheckOutDate(checkOutDate);

        if (room.getCurrentGuestList().size() == 1) {
            //pay only the first settled after the room was empty
            guestDao.updateGuestPrice(guest, room.getPrice());
        }
    }

    @Override
    @Transactional
    public void removeGuestFromRoom(long guestId) {
        Guest guest = guestDao.getById(guestId);
        if (Objects.isNull(guest.getRoom())) {
            return;
        }
        roomDao.removeGuest(guest.getRoom(), guest);
        guestDao.updateGuestPrice(guest, guest.getPrice() - guest.getRoom().getPrice());
        guestDao.updateGuestRoom(guest, null);
        guest.setCheckInDate(null);
        guest.setCheckOutDate(null);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED) //TODO: должно работать
    public void deleteGuest(long guestId) {
        removeGuestFromRoom(guestId);
        guestDao.delete(guestDao.getById(guestId));
    }

    private List<Guest> getGuestsSorted(SortEnum sortEnum, String order) {
        String fieldToSort = getFieldToSortFromEnum(sortEnum);
        return getAll(fieldToSort, order);
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
    public List<Guest> sortByAddition(String order) {
        return getGuestsSorted(SortEnum.BY_ADDITION, order);
    }

    @Override
    public List<Guest> sortByAlphabet(String order) {
        return getGuestsSorted(SortEnum.BY_ALPHABET, order);
    }

    @Override
    public List<Guest> sortByCheckOutDate(String order) {
        return getGuestsSorted(SortEnum.BY_CHECKOUT_DATE, order);
    }

    @Override
    public GuestDao getDefaultDao() {
        return guestDao;
    }
}
