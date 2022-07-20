package com.senla.controller.action.guest;

import com.senla.controller.action.AbstractAction;
import com.senla.controller.action.ConsoleReaderUtil;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;

import java.time.LocalDate;

public class CreateGuestAction extends AbstractAction {
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