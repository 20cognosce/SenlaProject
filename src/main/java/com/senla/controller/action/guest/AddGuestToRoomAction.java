package com.senla.controller.action.guest;

import com.senla.controller.action.AbstractAction;
import com.senla.controller.action.ConsoleReaderUtil;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;

public class AddGuestToRoomAction extends AbstractAction {
    public AddGuestToRoomAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long guestId = ConsoleReaderUtil.inputId("Введите идентификатор гостя: ");
        long roomId = ConsoleReaderUtil.inputId("Введите идентификатор комнаты: ");
        getGuestService().addGuestToRoom(guestId, roomId);
    }
}