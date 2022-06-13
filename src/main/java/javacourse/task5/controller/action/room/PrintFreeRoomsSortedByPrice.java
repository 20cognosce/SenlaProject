package javacourse.task5.controller.action.room;

import javacourse.task5.controller.action.AbstractAction;
import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PrintFreeRoomsSortedByPrice extends AbstractAction  {
    public PrintFreeRoomsSortedByPrice(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите на какую дату свободные номера вас интересуют [dd.MM.yyyy]: ");
        String str = scanner.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate specificDate = LocalDate.parse(str, dtf);
        System.out.println(getRoomService().sortFreeRoomsByPrice(specificDate));
    }
}