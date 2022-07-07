package com.senla.javacourse.dao.impl;

import com.senla.javacourse.dao.GuestDao;
import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.Room;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Repository
public class GuestDaoImpl extends AbstractDaoImpl<Guest> implements GuestDao {
    @PersistenceContext
    private EntityManager entityManager;

    public GuestDaoImpl() {
        super();
    }

    @Override
    public List<Guest> getAll(String fieldToSortBy) {
        String str = "select g from Guest g order by " + fieldToSortBy + " asc";
        TypedQuery<Guest> q = entityManager.createQuery(str, Guest.class);
        return q.getResultList();
    }

    @Override
    public Guest getById(long id) throws NoSuchElementException {
        Guest guest = entityManager.find(Guest.class, id);
        if (Objects.isNull(guest)) {
            throw new NoSuchElementException("Guest not found");
        } else {
            return guest;
        }
    }

    @Override
    public void updateGuestPrice(Guest guest, int price) {
        guest.setPrice(price);
        update(guest);
    }

    @Override
    public long getAllAmount() {
        TypedQuery<Long> q = entityManager.createQuery(
                "select count(*) from Guest", Long.class
        );
        return q.getSingleResult();
    }

    @Override
    public void updateGuestRoom(Guest guest, Room room) {
        guest.setRoom(room);
        update(guest);
    }

    @Override
    public String exportData(Guest guest) {
        return guest.getId() + "," +
                guest.getName() + "," +
                guest.getPassport() + "," +
                guest.getCheckInDate() + "," +
                guest.getCheckOutDate() + "," +
                guest.getRoom().getId();
    }
}
