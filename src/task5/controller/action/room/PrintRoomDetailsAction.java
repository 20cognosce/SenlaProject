
package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class PrintRoomDetailsAction extends AbstractAction implements IAction {
    public PrintRoomDetailsAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int roomId = ConsoleReaderUtil.inputRoomId();
        System.out.println(roomService.getById(roomId).getDetails());
    }
}