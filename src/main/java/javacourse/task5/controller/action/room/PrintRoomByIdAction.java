package javacourse.task5.controller.action.room;

import javacourse.task5.controller.action.AbstractAction;
import javacourse.task5.controller.action.ConsoleReaderUtil;
import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;

public class PrintRoomByIdAction extends AbstractAction  {
    public PrintRoomByIdAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long roomId = ConsoleReaderUtil.inputId("Введите идентификатор комнаты: ");
        System.out.println(getRoomService().getById(roomId).toString() + getRoomService().getGuestsList(roomId));
    }
}