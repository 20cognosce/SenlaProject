package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class PrintRoomByIdAction extends AbstractAction implements IAction {
    public PrintRoomByIdAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int roomId = inputRoomId();
        System.out.println(roomService.getById(roomId).toString() + roomService.getGuestsList(roomId));
    }
}