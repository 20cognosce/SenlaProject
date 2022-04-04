package task5.controller.action.guest;

import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;



public class RemoveGuestFromRoomAction extends PrintAllAction {
    public RemoveGuestFromRoomAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int guestId;

        try {
            guestId = inputGuestId();
            guestService.removeGuestFromRoom(guestId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}