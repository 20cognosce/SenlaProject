package com.senla.service.impl;

import com.senla.build.config.SortEnum;
import com.senla.dao.GuestDao;
import com.senla.dao.RoomDao;
import com.senla.model.Guest;
import com.senla.model.Guest_;
import com.senla.model.Maintenance;
import com.senla.model.Room;
import com.senla.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl extends AbstractServiceImpl<Guest, GuestDao> implements GuestService {

    private final GuestDao guestDao;
    private final RoomDao roomDao;

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
        //TODO: здесь работа в любом случае внутри транзакции так что можно оставить без root.fetch
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
    @Transactional(propagation = Propagation.NESTED) //TODO: должно работать
    public void deleteGuest(long guestId) {
        removeGuestFromRoom(guestId);
        guestDao.delete(guestDao.getById(guestId));
    }

    private List<Guest> getGuestsSorted(SortEnum sortEnum, String order) {
        String fieldToSort = getFieldToSortFromEnum(sortEnum);
        return getAll(fieldToSort, order); //this.getAll is a hack to enable @Transactional
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
    public List<Maintenance> getGuestMaintenanceList(long guestId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Guest> cq = cb.createQuery(Guest.class);
        Root<Guest> root = cq.from(Guest.class);
        root.fetch("orderedMaintenances", JoinType.LEFT);

        return entityManager.createQuery(cq
                .select(root)
                .where(cb.equal(root.get(Guest_.id), guestId))
        )
                .getSingleResult()
                .getOrderedMaintenances();
    }

    @Override
    public GuestDao getDefaultDao() {
        return guestDao;
    }
}
