package task5.controller.action.maintenance;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.controller.action.SortEnum;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class PrintAllSortedByCategory extends AbstractAction implements IAction {
    public PrintAllSortedByCategory(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println(maintenanceService.getSorted(maintenanceService.getAll(), SortEnum.BY_CATEGORY));
    }
}