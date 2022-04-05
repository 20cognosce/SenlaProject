package task5.controller.action.maintenance;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class OrderMaintenanceAction extends AbstractAction implements IAction {
    public OrderMaintenanceAction (GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int guestId = inputGuestId();
        int maintenanceId = inputMaintenanceId();
        maintenanceService.executeMaintenance(guestId, maintenanceId);
    }
}
