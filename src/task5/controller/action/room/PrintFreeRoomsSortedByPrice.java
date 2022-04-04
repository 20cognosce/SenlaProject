package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.dao.model.Room;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Scanner;

public class PrintFreeRoomsSortedByPrice extends AbstractAction implements IAction {
    public PrintFreeRoomsSortedByPrice(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        LocalDate specificDate;
        try {
            System.out.println("Введите на какую дату свободные номера вас интересуют [dd.MM.yyyy]: ");
            String str = scanner.nextLine();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            specificDate = LocalDate.parse(str, dtf);
            System.out.println(roomService.getAsString(roomService.getSorted(
                    roomService.getFree(specificDate), Comparator.comparingInt(Room::getPrice))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}