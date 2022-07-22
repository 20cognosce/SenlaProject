package com.senla.dao.impl;

import com.senla.dao.GuestDao;
import com.senla.model.Guest;
import com.senla.model.Room;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
@NoArgsConstructor
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

    @Override
    protected Class<Guest> daoEntityClass() {
        return Guest.class;
    }
}
