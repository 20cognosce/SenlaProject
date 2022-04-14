package task5.controller.action.maintenance;

import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class PrintMaintenancesOfGuestSortedByTime extends AbstractAction  {
    public PrintMaintenancesOfGuestSortedByTime(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long guestId = ConsoleReaderUtil.inputId();
        System.out.println(getMaintenanceService().sortMaintenancesOfGuestByTime(guestId));

    }
}