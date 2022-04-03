package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import javax.security.sasl.SaslClient;
import java.util.Scanner;


public class ChangeRoomPriceAction extends AbstractAction implements IAction {
    public ChangeRoomPriceAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int roomId;
        int price;
        try {
            roomId = inputRoomId();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите новую стоимость номера: ");
            price = scanner.nextInt();

            roomService.getRoomById(roomId).setPrice(price);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}