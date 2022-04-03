package task5.controller.action;

import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class AbstractAction {
    protected final GuestService guestService;
    protected final RoomService roomService;
    protected final MaintenanceService maintenanceService;

    public AbstractAction (GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        this.guestService = guestService;
        this.roomService = roomService;
        this.maintenanceService = maintenanceService;
    }

    protected int inputGuestId() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        int id;
        System.out.println("Введите идентификатор гостя: ");
        id = scanner.nextInt();
        return id;
    }

    protected int inputRoomId() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        int id;
        System.out.println("Введите идентификатор комнаты: ");
        id = scanner.nextInt();
        return id;
    }

    protected int inputMaintenanceId() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        int id;
        System.out.println("Введите идентификатор услуги: ");
        id = scanner.nextInt();
        return id;
    }
}
