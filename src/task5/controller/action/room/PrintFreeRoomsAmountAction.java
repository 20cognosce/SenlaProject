package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class PrintFreeRoomsAmountAction extends AbstractAction implements IAction {
    public PrintFreeRoomsAmountAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
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
            System.out.println("Количество свободных комнат на данный момент: " + roomService.getFree(specificDate).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}