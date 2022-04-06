package task5.controller.action.maintenance;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.dao.model.Maintenance;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.util.Comparator;


public class PrintMaintenancesOfGuestSortedByPrice extends AbstractAction implements IAction {
    public PrintMaintenancesOfGuestSortedByPrice(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int guestId = ConsoleReaderUtil.inputGuestId();
        System.out.println(maintenanceService.getMaintenancesOfGuest(guestId, Comparator.comparing(Maintenance::getPrice)));
    }
}