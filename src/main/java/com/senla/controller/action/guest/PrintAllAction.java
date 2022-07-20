package com.senla.controller.action.guest;

import com.senla.controller.action.AbstractAction;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;

public class PrintAllAction extends AbstractAction {
    public PrintAllAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println(getGuestService().sortByAddition());
    }
}