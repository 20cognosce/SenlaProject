package task5.controller.action.guest;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
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
        System.out.println("Введите полное имя гостя: ");
        String fullName = ConsoleReaderUtil.readLine();

        System.out.println("Введите паспорт гостя: ");
        String passport = ConsoleReaderUtil.readLine();

        System.out.println("Введите дату заезда гостя [dd.MM.yyyy]: ");
        LocalDate checkInTime = ConsoleReaderUtil.readDate();

        System.out.println("Введите дату выезда гостя [dd.MM.yyyy]: ");
        LocalDate checkOutTime = ConsoleReaderUtil.readDate();

        guestService.createGuest(fullName, passport, checkInTime, checkOutTime, 0);
    }
}