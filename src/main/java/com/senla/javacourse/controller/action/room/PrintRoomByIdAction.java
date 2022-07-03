package com.senla.javacourse.controller.action.room;

import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.controller.action.ConsoleReaderUtil;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.RoomService;

public class PrintRoomByIdAction extends AbstractAction {
    public PrintRoomByIdAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long roomId = ConsoleReaderUtil.inputId("Введите идентификатор комнаты: ");
        System.out.println(getRoomService().getById(roomId).toString() + getRoomService().getGuestsList(roomId));
    }
}