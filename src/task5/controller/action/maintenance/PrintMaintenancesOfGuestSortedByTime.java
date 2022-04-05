package task5.controller.action.maintenance;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.dao.model.Maintenance;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.util.Comparator;

public class PrintMaintenancesOfGuestSortedByTime extends AbstractAction implements IAction {
    public PrintMaintenancesOfGuestSortedByTime(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int guestId = inputGuestId();
        System.out.println(maintenanceService.getMaintenancesOfGuest(
                guestId, Comparator.comparing(Maintenance::getOrderTime)));

    }
}