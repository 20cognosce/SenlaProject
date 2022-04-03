package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.dao.model.Room;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.util.Comparator;


public class PrintAllSortedByCapacity extends AbstractAction implements IAction {
    public PrintAllSortedByCapacity(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println(roomService.getAllAsString(roomService.getSorted(
                roomService.getAll(), Comparator.comparingInt(Room::getCapacity)
        )));
    }
}