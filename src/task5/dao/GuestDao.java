package task5.dao;

import task5.dao.entity.Guest;

public interface GuestDao extends AbstractDao<Guest> {
    void updatePrice(Guest guest);
}
