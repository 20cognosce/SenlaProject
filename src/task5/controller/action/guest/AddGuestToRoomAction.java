package task5.controller.action.guest;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class AddGuestToRoomAction extends AbstractAction implements IAction {
    public AddGuestToRoomAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int guestId = inputGuestId();
        int roomId = inputRoomId();
        guestService.addGuestToRoom(guestId, roomId);
    }
}