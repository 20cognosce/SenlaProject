package javacourse.task5.controller.action.maintenance;

import javacourse.task5.controller.action.AbstractAction;
import javacourse.task5.controller.action.ConsoleReaderUtil;
import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;

public class OrderMaintenanceAction extends AbstractAction  {
    public OrderMaintenanceAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long guestId = ConsoleReaderUtil.inputId("Введите идентификатор гостя: ");
        long maintenanceId = ConsoleReaderUtil.inputId("Введите идентификатор услуги: ");
        getMaintenanceService().executeMaintenance(guestId, maintenanceId);
    }
}
