package task5.dao.impl;

import task5.dao.GuestDao;
import task5.dao.model.Guest;
import task5.dao.model.Room;

import java.util.Objects;

public class GuestDaoImpl extends AbstractDaoImpl<Guest> implements GuestDao {
    public GuestDaoImpl() {
        super();
    }

    @Override
    public void updatePrice(Guest guest) {
        Room room = guest.getRoom();
        //pay only the first settled after the room was empty
        if (!Objects.isNull(room) && room.getGuestsCurrentList().size() == 1) {
            guest.setPrice(room.getPrice());
        }
    }

    @Override
    public String exportData(Guest guest) {
        StringBuilder line = new StringBuilder();
        long roomId;
        try {
            roomId = guest.getRoom().getId();
        } catch (NullPointerException e) {
            roomId = 0L;
        }
        line.append(guest.getId()).append(",")
                .append(guest.getName()).append(",")
                .append(guest.getPassport()).append(",")
                .append(guest.getCheckInDate()).append(",")
                .append(guest.getCheckOutDate()).append(",")
                .append(roomId);
        return line.toString();
    }
}
