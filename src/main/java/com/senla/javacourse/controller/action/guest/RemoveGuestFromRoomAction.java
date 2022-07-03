package com.senla.javacourse.controller.action.guest;

import com.senla.javacourse.controller.action.ConsoleReaderUtil;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.RoomService;

public class RemoveGuestFromRoomAction extends PrintAllAction {
    public RemoveGuestFromRoomAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long guestId = ConsoleReaderUtil.inputId("Введите идентификатор гостя: ");
        getGuestService().removeGuestFromRoom(guestId);
    }
}