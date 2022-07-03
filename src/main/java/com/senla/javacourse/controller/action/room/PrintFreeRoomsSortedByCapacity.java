
package com.senla.javacourse.controller.action.room;

import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PrintFreeRoomsSortedByCapacity extends AbstractAction {
    public PrintFreeRoomsSortedByCapacity(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите на какую дату свободные номера вас интересуют [dd.MM.yyyy]: ");
        String str = scanner.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate specificDate = LocalDate.parse(str, dtf);
        System.out.println(getRoomService().sortFreeRoomsByCapacity(specificDate));
    }
}