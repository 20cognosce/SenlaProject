package com.senla.javacourse.controller.action.guest;

import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.controller.action.ConsoleReaderUtil;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.RoomService;

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