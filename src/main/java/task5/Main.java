package task5;

import task5.build.factory.Application;
import task5.build.factory.ApplicationContext;
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

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run(new HashMap<>(Map.of(
                RoomDao.class, RoomDaoImpl.class,
                GuestDao.class, GuestDaoImpl.class,
                MaintenanceDao.class, MaintenanceDaoImpl.class,
                RoomService.class, RoomServiceImpl.class,
                GuestService.class, GuestServiceImpl.class,
                MaintenanceService.class, MaintenanceServiceImpl.class,
                Builder.class, Builder.class,
                Navigator.class, Navigator.class,
                MenuController.class, MenuController.class)));

        Builder builder = context.getObject(Builder.class);
        builder.buildMenu();
        MenuController menuController = context.getObject(MenuController.class);
        menuController.run();
    }
}
