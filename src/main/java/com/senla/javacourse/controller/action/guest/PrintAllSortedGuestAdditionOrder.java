package com.senla.javacourse.controller.action.guest;

import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.RoomService;

public class PrintAllSortedGuestAdditionOrder extends AbstractAction  {
    public PrintAllSortedGuestAdditionOrder(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println(getGuestService().sortByAddition());
    }
}