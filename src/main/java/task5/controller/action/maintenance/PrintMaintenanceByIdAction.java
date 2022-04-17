package task5.controller.action.maintenance;

import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;


public class PrintMaintenanceByIdAction extends AbstractAction  {
    public PrintMaintenanceByIdAction (GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long maintenanceId = ConsoleReaderUtil.inputId();
        System.out.println(getMaintenanceService().getById(maintenanceId).toString());
    }
}