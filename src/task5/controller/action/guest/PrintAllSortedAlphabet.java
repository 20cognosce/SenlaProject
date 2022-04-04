package task5.controller.action.guest;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.dao.model.Guest;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.util.Comparator;

public class PrintAllSortedAlphabet extends AbstractAction implements IAction {
    public PrintAllSortedAlphabet(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println(guestService.getAllAsString(
                guestService.getSorted(guestService.getAll(), Comparator.comparing(Guest::getName))));
    }
}