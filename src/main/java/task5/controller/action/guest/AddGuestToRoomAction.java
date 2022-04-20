package task5.controller.action.guest;

import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class AddGuestToRoomAction extends AbstractAction {
    public AddGuestToRoomAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long guestId = ConsoleReaderUtil.inputId("Введите идентификатор гостя: ");
        long roomId = ConsoleReaderUtil.inputId("Введите идентификатор комнаты: ");
        getGuestService().addGuestToRoom(guestId, roomId);
    }
}