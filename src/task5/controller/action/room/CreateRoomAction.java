package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.dao.model.RoomStatus;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;


public class CreateRoomAction extends AbstractAction implements IAction {
    public CreateRoomAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int roomStatusIndex = -1;
        System.out.println("Введите номер комнаты: ");
        String roomName = ConsoleReaderUtil.readLine();
        System.out.println("Введите вместимость номера: ");
        int capacity = ConsoleReaderUtil.readInteger();
        System.out.println("Введите количество звёзд номера: ");
        int stars = ConsoleReaderUtil.readInteger();
        System.out.println("Введите суточную стоимость номера: ");
        int price = ConsoleReaderUtil.readInteger();

        System.out.println("Выберите текущий статус номера (индекс): ");
        RoomStatus[] roomStatuses = RoomStatus.values();
        int i = 0;
        while (i < roomStatuses.length) {
            System.out.println(i + 1 + ". " + roomStatuses[i]);
            ++i;
        }
        while (roomStatusIndex < 1 || roomStatusIndex > roomStatuses.length) {
            roomStatusIndex = ConsoleReaderUtil.readInteger();
        }

        roomService.createRoom(roomName, capacity, stars, roomStatuses[roomStatusIndex - 1], price);
    }
}