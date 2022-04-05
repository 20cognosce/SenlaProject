package task5.controller.action.guest;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.controller.action.SortEnum;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class PrintAllSortedByCheckoutDate extends AbstractAction implements IAction {
    public PrintAllSortedByCheckoutDate(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println(guestService.getSorted(guestService.getAll(), SortEnum.BY_CHECKOUT_DATE));
    }
}