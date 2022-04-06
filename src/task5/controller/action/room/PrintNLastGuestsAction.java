package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class PrintNLastGuestsAction extends AbstractAction implements IAction {
    public PrintNLastGuestsAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int roomId = ConsoleReaderUtil.inputRoomId();
        System.out.println("Введите количество последних гостей номера для показа информации: ");
        int number = ConsoleReaderUtil.readInteger();
        System.out.println(roomService.getLastNGuests(roomId, number));
    }
}