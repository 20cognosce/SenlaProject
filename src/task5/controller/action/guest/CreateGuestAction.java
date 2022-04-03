package task5.controller.action.guest;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CreateGuestAction extends AbstractAction implements IAction {
    public CreateGuestAction (GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        String str;
        DateTimeFormatter dtf;
        String fullName;
        String passport;
        LocalDate checkInTime;
        LocalDate checkOutTime;

        try {
            System.out.println("Введите полное имя гостя: ");
            fullName = scanner.nextLine();

            System.out.println("Введите паспорт гостя: ");
            passport = scanner.nextLine();

            System.out.println("Введите дату заезда гостя [dd.MM.yyyy]: ");
            str = scanner.nextLine();
            dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            checkInTime = LocalDate.parse(str, dtf);

            System.out.println("Введите дату выезда гостя [dd.MM.yyyy]: ");
            str = scanner.nextLine();
            dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            checkOutTime = LocalDate.parse(str, dtf);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        guestService.createGuest(fullName, passport, checkInTime, checkOutTime, null);
    }
}