package task5.controller.action.maintenance;

import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class PrintAllSortedMaintenanceAdditionOrder extends AbstractAction  {
    public PrintAllSortedMaintenanceAdditionOrder(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println(getMaintenanceService().sortByAddition());
    }
}