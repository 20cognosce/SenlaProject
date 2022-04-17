package task5.controller.action.room;

import task5.controller.PropertiesUtil;
import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class PrintNLastGuestsAction extends AbstractAction  {
    public PrintNLastGuestsAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long roomId = ConsoleReaderUtil.inputId("Введите идентификатор комнаты: ");
        int number = Integer.parseInt(PropertiesUtil.property.getProperty("GuestsNumberInRoomHistory"));
        getRoomService().getLastNGuests(roomId, number).forEach(guest ->
                System.out.println(guest.toString() + guest.getOrderedMaintenances() + "\n"));
    }
}