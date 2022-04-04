package task5.controller.action.maintenance;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;


public class PrintMaintenancesOfGuestSortedNot extends AbstractAction implements IAction {
    public PrintMaintenancesOfGuestSortedNot(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int guestId;
        try {
            guestId = inputGuestId();
            System.out.println(maintenanceService.getMaintenancesOfGuest(guestId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}