package javacourse.task5.controller.action.guest;

import javacourse.task5.controller.action.AbstractAction;
import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;

public class PrintAllSortedGuestAdditionOrder extends AbstractAction  {
    public PrintAllSortedGuestAdditionOrder(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println(getGuestService().sortByAddition());
    }
}