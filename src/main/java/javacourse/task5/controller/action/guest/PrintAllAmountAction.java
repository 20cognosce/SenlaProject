package javacourse.task5.controller.action.guest;

import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;


public class PrintAllAmountAction extends PrintAllAction {
    public PrintAllAmountAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println("Общее число гостей: " + getGuestService().getAllAmount());
    }
}