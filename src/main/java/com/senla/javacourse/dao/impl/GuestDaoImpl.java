package com.senla.javacourse.dao.impl;

import com.senla.javacourse.dao.entity.Room;
import com.senla.javacourse.dao.GuestDao;
import com.senla.javacourse.dao.entity.Guest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class GuestDaoImpl extends AbstractDaoImpl<Guest> implements GuestDao {
    public GuestDaoImpl() {
        super();
    }

    @Override
    public void updateGuestPrice(Guest guest, int price) {
        openSessionAndExecuteTransactionTask(((session, criteriaBuilder) -> {
            guest.setPrice(price);
            session.update(guest);
        }));
    }

    @Override
    public void updateGuestRoom(Guest guest, Room room) { //TODO: check null option in service
        openSessionAndExecuteTransactionTask(((session, criteriaBuilder) -> {
            guest.setRoom(room);
            session.update(guest);
        }));
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
    public Guest getDaoEntity() {
        return new Guest();
    }
}
