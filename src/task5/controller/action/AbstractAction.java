package task5.controller.action;

import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public abstract class AbstractAction {
    protected final GuestService guestService;
    protected final RoomService roomService;
    protected final MaintenanceService maintenanceService;

    public AbstractAction (GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        this.guestService = guestService;
        this.roomService = roomService;
        this.maintenanceService = maintenanceService;
    }
}
