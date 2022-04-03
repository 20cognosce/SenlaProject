package task5.service.impl;

import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;

public abstract class AbstractServiceImpl {
    protected GuestDao guestDao;
    protected RoomDao roomDao;
    protected MaintenanceDao maintenanceDao;

    public AbstractServiceImpl (GuestDao guestDao, RoomDao roomDao, MaintenanceDao maintenanceDao) {
        this.guestDao = guestDao;
        this.maintenanceDao = maintenanceDao;
        this.roomDao = roomDao;
    }


}
