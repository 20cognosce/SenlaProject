package com.senla.dao.impl;

import com.senla.dao.GuestDao;
import com.senla.model.Guest;
import com.senla.model.Room;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class GuestDaoImpl extends AbstractDaoImpl<Guest> implements GuestDao {

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
    public Guest getGuestByLogin(String login) {
       TypedQuery<Guest> q = entityManager.createQuery(
                "select g from Guest g where g.login = :login", Guest.class
        );
       q.setParameter("login", login);
       return q.getSingleResult();
    }

    @Override
    public void updateGuestRoom(Guest guest, Room room) {
        guest.setRoom(room);
        update(guest);
    }

    @Override
    protected Class<Guest> daoEntityClass() {
        return Guest.class;
    }
}
