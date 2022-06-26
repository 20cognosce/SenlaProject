package javacourse.task5.dao;

import javacourse.task5.dao.entity.Guest;
import javacourse.task5.dao.entity.Room;

public interface GuestDao extends AbstractDao<Guest> {
    void updateGuestPrice(Guest guest, int price);
    void updateGuestRoom(Guest guest, Room room);
}
