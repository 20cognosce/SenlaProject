package task5.controller.action.room;

import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.dao.entity.RoomStatus;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;


public class CreateRoomAction extends AbstractAction  {
    public CreateRoomAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println("Введите номер комнаты: ");
        String roomName = ConsoleReaderUtil.readLine();
        System.out.println("Введите вместимость номера: ");
        int capacity = ConsoleReaderUtil.readInteger();
        System.out.println("Введите количество звёзд номера: ");
        int stars = ConsoleReaderUtil.readInteger();
        System.out.println("Введите суточную стоимость номера: ");
        int price = ConsoleReaderUtil.readInteger();
        RoomStatus roomStatus = ConsoleReaderUtil.inputRoomStatus();

        getRoomService().createRoom(roomName, capacity, stars, roomStatus, price);
    }
}