package javacourse.task5.controller.action.guest;

import javacourse.task5.controller.action.AbstractAction;
import javacourse.task5.controller.action.ConsoleReaderUtil;
import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;

import java.time.LocalDate;

public class CreateGuestAction extends AbstractAction  {
    public CreateGuestAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
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

        getGuestService().createGuest(fullName, passport, checkInTime, checkOutTime, 0);
    }
}