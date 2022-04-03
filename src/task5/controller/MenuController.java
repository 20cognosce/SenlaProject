package task5.controller;

import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.util.Scanner;

public class MenuController {
    Builder builder;
    Navigator navigator;

    public MenuController (GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        builder = new Builder(guestService, roomService, maintenanceService);
    }

    public void run() {
        builder.buildMenu();
        navigator = new Navigator(builder.getRootMenu());

        Scanner scanner = new Scanner(System.in);
        navigator.printMenu();
        int index;
        while (true) {
            index = scanner.nextInt();
            if (index == 0) return;

            try {
                navigator.navigate(index);
            } catch (Exception e) {
                e.printStackTrace();
                navigator.printMenu();
                continue;
            }
            navigator.printMenu();
        }
    }
}
