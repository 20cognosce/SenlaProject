package javacourse.task5.dao.impl;

import javacourse.task5.build.factory.Component;
import javacourse.task5.dao.GuestDao;
import javacourse.task5.dao.entity.Guest;
import javacourse.task5.dao.entity.Room;


@Component
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
