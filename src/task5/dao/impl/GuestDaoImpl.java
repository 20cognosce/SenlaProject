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
            guest.setPrice(guest.getPrice() + room.getPrice());
        }
    }
}
