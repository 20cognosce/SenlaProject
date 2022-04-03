package task5;

import task5.controller.MenuController;
import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.impl.GuestDaoImpl;
import task5.dao.impl.MaintenanceDaoImpl;
import task5.dao.impl.RoomDaoImpl;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;
import task5.service.impl.GuestServiceImpl;
import task5.service.impl.MaintenanceServiceImpl;
import task5.service.impl.RoomServiceImpl;

public class Main {
    public static void main(String[] args) {
        RoomDao roomDao = new RoomDaoImpl();
        GuestDao guestDao = new GuestDaoImpl();
        MaintenanceDao maintenanceDao = new MaintenanceDaoImpl();

        RoomService roomService = new RoomServiceImpl(guestDao, roomDao, maintenanceDao);
        GuestService guestService = new GuestServiceImpl(guestDao, roomDao, maintenanceDao);
        MaintenanceService maintenanceService = new MaintenanceServiceImpl(guestDao, roomDao, maintenanceDao);

        MenuController menuController = new MenuController(guestService, roomService, maintenanceService);
        menuController.run();
    }
}
