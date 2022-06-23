package javacourse.task5.controller.action.room;

import javacourse.task5.controller.action.AbstractAction;
import javacourse.task5.controller.action.ConsoleReaderUtil;
import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;


public class ChangeRoomPriceAction extends AbstractAction  {
    public ChangeRoomPriceAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long roomId = ConsoleReaderUtil.inputId("Введите идентификатор комнаты: ");
        System.out.println("Введите новую стоимость номера: ");
        int price = ConsoleReaderUtil.readInteger();
        getRoomService().updateRoomPrice(roomId, price);
    }
}