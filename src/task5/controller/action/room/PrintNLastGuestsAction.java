package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.util.Scanner;

public class PrintNLastGuestsAction extends AbstractAction implements IAction {
    public PrintNLastGuestsAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        int roomId = inputRoomId();
        System.out.println("Введите количество последних гостей номера для показа информации: ");
        int number = scanner.nextInt();
        System.out.println(roomService.getLastNGuests(roomId, number));
    }
}