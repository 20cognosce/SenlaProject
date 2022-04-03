package task5.controller.action.guest;

import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;


public class PrintAllAmountAction extends PrintAllAction {
    public PrintAllAmountAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println("Общее число гостей: " + guestService.getAmount(guestService.getAll()));
    }
}