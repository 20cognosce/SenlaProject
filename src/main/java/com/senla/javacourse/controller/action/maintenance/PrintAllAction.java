package com.senla.javacourse.controller.action.maintenance;

import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.RoomService;

public class PrintAllAction extends AbstractAction {
    public PrintAllAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println(getMaintenanceService().sortByAddition());
    }
}