package com.senla.controller.action.guest;

import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;


public class PrintAllAmountAction extends PrintAllAction {
    public PrintAllAmountAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println("Общее число гостей: " + getGuestService().getAllAmount());
    }
}