package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.util.Scanner;


public class ChangeRoomPriceAction extends AbstractAction implements IAction {
    public ChangeRoomPriceAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        int roomId = inputRoomId();
        System.out.println("Введите новую стоимость номера: ");
        int price = scanner.nextInt();
        roomService.getById(roomId).setPrice(price);
    }
}