package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class PrintFreeRoomsSortedNot extends AbstractAction implements IAction {
    public PrintFreeRoomsSortedNot(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
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
            System.out.println(roomService.getAllAsString(roomService.getFree(specificDate)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}