package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.dao.model.RoomStatus;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CreateRoomAction extends AbstractAction implements IAction {
    public CreateRoomAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        int capacity;
        int roomNumber = -1;
        int stars;
        int roomStatusIndex = -1;
        int price;

        try {
            System.out.println("Введите номер комнаты (больше 0): ");
            while (roomNumber <= 0) {
                roomNumber = scanner.nextInt();
            }
            System.out.println("Введите вместимость номера: ");
            capacity = scanner.nextInt();
            System.out.println("Введите количество звёзд номера: ");
            stars = scanner.nextInt();
            System.out.println("Введите суточную стоимость номера: ");
            price = scanner.nextInt();

            System.out.println("Выберите текущий статус номера (индекс): ");
            ArrayList<RoomStatus> roomStatuses = new ArrayList<>(Arrays.asList(RoomStatus.values()));
            int i = 1;
            while (i < roomStatuses.size()) {
                System.out.println(i + ". " + roomStatuses.get(i - 1));
                ++i;
            }
            while (roomStatusIndex < 1 || roomStatusIndex > roomStatuses.size()) {
                roomStatusIndex = scanner.nextInt();
            }

            roomService.createRoom(roomNumber, capacity, stars, roomStatuses.get(roomStatusIndex - 1), price);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}