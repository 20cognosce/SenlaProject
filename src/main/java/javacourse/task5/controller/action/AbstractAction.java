package javacourse.task5.controller.action;

import javacourse.task5.controller.IAction;
import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;

public abstract class AbstractAction implements IAction {
    private final GuestService guestService;
    private final RoomService roomService;
    private final MaintenanceService maintenanceService;

    public AbstractAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        this.guestService = guestService;
        this.roomService = roomService;
        this.maintenanceService = maintenanceService;
    }

    public GuestService getGuestService() {
        return guestService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public MaintenanceService getMaintenanceService() {
        return maintenanceService;
    }
}
