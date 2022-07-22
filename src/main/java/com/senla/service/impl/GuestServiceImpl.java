package com.senla.service.impl;

import com.senla.build.config.SortEnum;
import com.senla.dao.GuestDao;
import com.senla.dao.MaintenanceDao;
import com.senla.dao.RoomDao;
import com.senla.model.Guest;
import com.senla.model.Maintenance;
import com.senla.model.Room;
import com.senla.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl extends AbstractServiceImpl<Guest, GuestDao> implements GuestService {

    private final GuestDao guestDao;
    private final RoomDao roomDao;
    private final MaintenanceDao maintenanceDao;

    @Override
    @Transactional
    //Accept only without room
    public void createGuest(Guest guest) {
        if (Objects.isNull(guest.getRoom())) {
            guestDao.create(guest);
        } else {
            throw new IllegalArgumentException("Creating only a guest without room is possible");
        }
    }

    @Override
    @Transactional
    public void addGuestToRoom(long guestId, long roomId) {
        Guest guest = guestDao.getById(guestId);
        Room room = roomDao.getById(roomId);
        if (room.isUnavailableToSettle()) {
            throw new RuntimeException("Room is unavailable");
        }
        if (!Objects.isNull(guest.getRoom())) {
            throw new IllegalArgumentException("Guest has room already. Remove it first");
        }
        roomDao.addGuestToRoom(room, guest);
        guestDao.updateGuestRoom(guest, room);

        Room roomUpdated = roomDao.getById(roomId);
        if (roomDao.getGuestsOfRoom(roomUpdated).size() == 1) {
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
    @Transactional(propagation = Propagation.NESTED) //TODO: должно работать
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
    public List<Maintenance> getGuestMaintenanceList(long guestId) {
        Guest guest = getById(guestId);
        return maintenanceDao.getMaintenancesOfGuest(guest, "id");
    }

    @Override
    public GuestDao getDefaultDao() {
        return guestDao;
    }
}
