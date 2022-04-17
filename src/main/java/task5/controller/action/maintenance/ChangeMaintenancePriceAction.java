package task5.controller.action.maintenance;

import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class ChangeMaintenancePriceAction extends AbstractAction  {
    public ChangeMaintenancePriceAction (GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long maintenanceId = ConsoleReaderUtil.inputId("Введите идентификатор услуги: ");
        System.out.println("Введите новую стоимость услуги: ");
        int maintenancePrice = ConsoleReaderUtil.readInteger();
        getMaintenanceService().setPrice(maintenanceId, maintenancePrice);
    }
}