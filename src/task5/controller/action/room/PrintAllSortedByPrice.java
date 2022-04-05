package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import static task5.controller.action.SortEnum.BY_PRICE;


public class PrintAllSortedByPrice extends AbstractAction implements IAction {
    public PrintAllSortedByPrice(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println(roomService.getSorted(roomService.getAll(), BY_PRICE));
    }
}