package task5.dao;

import task5.dao.model.Guest;

public interface GuestDao extends AbstractDao<Guest> {
    void updatePrice(Guest guest);
    String convertDataToExportFormat(Guest guest);
}
