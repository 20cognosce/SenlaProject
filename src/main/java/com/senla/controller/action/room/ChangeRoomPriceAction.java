package com.senla.controller.action.room;

import com.senla.controller.action.AbstractAction;
import com.senla.controller.action.ConsoleReaderUtil;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;


public class ChangeRoomPriceAction extends AbstractAction {
    public ChangeRoomPriceAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long roomId = ConsoleReaderUtil.inputId("Введите идентификатор комнаты: ");
        System.out.println("Введите новую стоимость номера: ");
        int price = ConsoleReaderUtil.readInteger();
        getRoomService().updateRoomPrice(roomId, price);
    }
}