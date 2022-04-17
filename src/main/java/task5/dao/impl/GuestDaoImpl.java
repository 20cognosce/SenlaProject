package task5.dao.impl;

import task5.dao.GuestDao;
import task5.dao.entity.Guest;
import task5.dao.entity.Room;

import java.util.Objects;

public class GuestDaoImpl extends AbstractDaoImpl<Guest> implements GuestDao {
    public GuestDaoImpl() {
        super();
    }

    @Override
    public void updatePrice(Guest guest, Room room) {
        //pay only the first settled after the room was empty
        if (!Objects.isNull(room) && room.getCurrentGuestIdList().size() == 1) {
            guest.setPrice(room.getPrice());
        }
    }

    @Override
    public String exportData(Guest guest) {
        return guest.getId() + "," +
                guest.getName() + "," +
                guest.getPassport() + "," +
                guest.getCheckInDate() + "," +
                guest.getCheckOutDate() + "," +
                guest.getRoomId();
    }
}
