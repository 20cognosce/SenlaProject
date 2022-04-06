package task5.controller.action.maintenance;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class ChangeMaintenancePriceAction extends AbstractAction implements IAction {
    public ChangeMaintenancePriceAction (GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int maintenanceId = ConsoleReaderUtil.inputMaintenanceId();
        System.out.println("Введите новую стоимость услуги: ");
        int maintenancePrice = ConsoleReaderUtil.readInteger();
        maintenanceService.setPrice(maintenanceId, maintenancePrice);
    }
}