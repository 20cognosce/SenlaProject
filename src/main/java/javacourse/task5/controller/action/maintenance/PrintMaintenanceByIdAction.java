package javacourse.task5.controller.action.maintenance;

import javacourse.task5.controller.action.AbstractAction;
import javacourse.task5.controller.action.ConsoleReaderUtil;
import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;


public class PrintMaintenanceByIdAction extends AbstractAction  {
    public PrintMaintenanceByIdAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long maintenanceId = ConsoleReaderUtil.inputId("Введите идентификатор услуги: ");
        System.out.println(getMaintenanceService().getById(maintenanceId).toString());
    }
}