package com.senla.javacourse.controller.action.room;

import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.controller.action.ConsoleReaderUtil;
import com.senla.javacourse.dao.entity.RoomStatus;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.RoomService;


public class CreateRoomAction extends AbstractAction {
    public CreateRoomAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println("Введите номер комнаты: ");
        String roomName = ConsoleReaderUtil.readLine();
        System.out.println("Введите вместимость номера: ");
        int capacity = ConsoleReaderUtil.readInteger();
        System.out.println("Введите количество звёзд номера: ");
        int stars = ConsoleReaderUtil.readInteger();
        System.out.println("Введите суточную стоимость номера: ");
        int price = ConsoleReaderUtil.readInteger();
        RoomStatus roomStatus = ConsoleReaderUtil.inputRoomStatus();

        getRoomService().createRoom(roomName, capacity, stars, roomStatus, price);
    }
}