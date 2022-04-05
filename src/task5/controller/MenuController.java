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
        builder.buildMenu();
        navigator = new Navigator(builder.getRootMenu());
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int index = -1;

        while (index != 0) {
            navigator.printMenu();
            /*because nextInt() doesn't read newline provided by hitting enter
            and nextLine() read it, so I would have to use two nextLine() after*/
            try {
                index = Integer.parseInt(scanner.nextLine());
                navigator.navigate(index);
                continue;
            } catch (CantNavigateFurtherException e) {
                try {
                    navigator.doAction();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } catch (IndexOutOfBoundsException e) {
                continue;
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Press Enter key to continue...");
            scanner.nextLine();
        }
    }
}
