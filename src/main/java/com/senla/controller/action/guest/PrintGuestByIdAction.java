package com.senla.controller.action.guest;

import com.senla.controller.action.AbstractAction;
import com.senla.controller.action.ConsoleReaderUtil;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;

public class PrintGuestByIdAction extends AbstractAction {
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