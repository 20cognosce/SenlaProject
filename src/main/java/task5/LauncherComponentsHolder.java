package task5;

import task5.config.CI.Configurable;
import task5.config.DI.DependencyInjectable;
import task5.controller.Builder;
import task5.controller.MenuController;
import task5.controller.Navigator;
import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class LauncherComponentsHolder {
    @Configurable
    public RoomDao roomDao;
    @Configurable
    public GuestDao guestDao;
    @Configurable
    public MaintenanceDao maintenanceDao;

    @DependencyInjectable
    public RoomService roomService;
    @DependencyInjectable
    public GuestService guestService;
    @DependencyInjectable
    public MaintenanceService maintenanceService;
    @DependencyInjectable
    public Builder builder;
    @DependencyInjectable
    public Navigator navigator;
    @DependencyInjectable
    public MenuController menuController;
}
