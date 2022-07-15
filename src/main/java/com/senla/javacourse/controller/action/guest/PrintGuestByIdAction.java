package com.senla.javacourse.controller.action.guest;

import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.controller.action.ConsoleReaderUtil;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.RoomService;

public class PrintGuestByIdAction extends AbstractAction  {
    public PrintGuestByIdAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
            super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long guestId = ConsoleReaderUtil.inputId("Введите идентификатор гостя: ");
        System.out.println(getGuestService().getById(guestId).toString()
                + getGuestService().getGuestMaintenanceList(guestId));
    }
}