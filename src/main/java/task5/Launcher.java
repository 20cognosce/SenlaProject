package task5;

import task5.config.CI.ConfigInjector;
import task5.config.DI.DependencyInjector;
import task5.controller.Builder;
import task5.controller.MenuController;
import task5.controller.Navigator;
import task5.dao.impl.GuestDaoImpl;
import task5.dao.impl.MaintenanceDaoImpl;
import task5.dao.impl.RoomDaoImpl;
import task5.service.impl.GuestServiceImpl;
import task5.service.impl.MaintenanceServiceImpl;
import task5.service.impl.RoomServiceImpl;

public class Launcher {
    public static void main(String[] args) {
        LauncherComponentsHolder root = new LauncherComponentsHolder();
        DependencyInjector dependencyInjector = new DependencyInjector(root);

        root.roomDao = new RoomDaoImpl();
        root.guestDao = new GuestDaoImpl();
        root.maintenanceDao = new MaintenanceDaoImpl();
        ConfigInjector.injectConfiguration(root.roomDao);
        ConfigInjector.injectConfiguration(root.guestDao);
        ConfigInjector.injectConfiguration(root.maintenanceDao);

        root.roomService = new RoomServiceImpl();
        root.guestService = new GuestServiceImpl();
        root.maintenanceService = new MaintenanceServiceImpl();
        dependencyInjector.injectDependency(root.roomService);
        dependencyInjector.injectDependency(root.guestService);
        dependencyInjector.injectDependency(root.maintenanceService);

        root.builder = new Builder();
        root.navigator = new Navigator();
        root.menuController = new MenuController();
        dependencyInjector.injectDependency(root.builder);
        dependencyInjector.injectDependency(root.navigator);
        dependencyInjector.injectDependency(root.menuController);

        root.builder.buildMenu();
        root.menuController.run();
    }
}
