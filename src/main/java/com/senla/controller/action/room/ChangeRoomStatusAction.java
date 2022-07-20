package com.senla.controller.action.room;

import com.senla.controller.action.AbstractAction;
import com.senla.controller.action.ConsoleReaderUtil;
import com.senla.model.RoomStatus;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ChangeRoomStatusAction extends AbstractAction {
    public ChangeRoomStatusAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int roomStatusIndex = -1;
        long roomId;
        ArrayList<RoomStatus> roomStatuses;

        try {
            roomId = ConsoleReaderUtil.inputId("Введите идентификатор комнаты: ");
            Scanner scanner = new Scanner(System.in);

            System.out.println("Выберите статус номера (индекс): ");
            roomStatuses = new ArrayList<>(Arrays.asList(RoomStatus.values()));
            int i = 1;
            while (i < roomStatuses.size()) {
                System.out.println(i + ". " + roomStatuses.get(i - 1));
                ++i;
            }
            while (roomStatusIndex < 1 || roomStatusIndex > roomStatuses.size()) {
                roomStatusIndex = scanner.nextInt();
            }
            getRoomService().updateRoomStatus(roomId, roomStatuses.get(roomStatusIndex - 1));
        } catch (Exception e) {
            System.out.println(e.getClass().getCanonicalName() + ": " + e.getMessage());
        }
    }
}