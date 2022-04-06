package task5.controller.action.guest;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;


public class DeleteGuestAction extends AbstractAction implements IAction {
    public DeleteGuestAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int guestId = ConsoleReaderUtil.inputGuestId();
        guestService.deleteGuest(guestId);
    }
}