package task5;

import task5.config.ConfigInstaller;
import task5.controller.Builder;
import task5.controller.MenuController;
import task5.controller.Navigator;
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

        ConfigInstaller configInstaller = new ConfigInstaller();
        configInstaller.installConfiguration(roomDao);
        configInstaller.installConfiguration(guestDao);
        configInstaller.installConfiguration(maintenanceDao);

        RoomService roomService = new RoomServiceImpl(guestDao, roomDao, maintenanceDao);
        GuestService guestService = new GuestServiceImpl(guestDao, roomDao, maintenanceDao);
        MaintenanceService maintenanceService = new MaintenanceServiceImpl(guestDao, roomDao, maintenanceDao);

        Builder builder = new Builder(guestService, roomService, maintenanceService);
        builder.buildMenu();
        Navigator navigator = new Navigator(builder.getRootMenu());
        MenuController menuController = new MenuController(builder, navigator);

        menuController.run();
    }
}
