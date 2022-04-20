package task5.controller.action.room;

import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PrintFreeRoomsAmountAction extends AbstractAction  {
    public PrintFreeRoomsAmountAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите на какую дату свободные номера вас интересуют [dd.MM.yyyy]: ");
        String str = scanner.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate specificDate = LocalDate.parse(str, dtf);
        System.out.println("Количество свободных комнат на данный момент: " + getRoomService().getFree(specificDate).size());
    }
}