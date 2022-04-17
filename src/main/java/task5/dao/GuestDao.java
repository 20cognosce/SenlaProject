package task5.dao;

import task5.dao.entity.Guest;
import task5.dao.entity.Room;

public interface GuestDao extends AbstractDao<Guest> {
    void updatePrice(Guest guest, Room room);
}
