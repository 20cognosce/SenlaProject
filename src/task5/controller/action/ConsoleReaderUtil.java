package task5.controller.action;

import task5.dao.model.MaintenanceCategory;
import task5.dao.model.RoomStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleReaderUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine() {
        return scanner.nextLine();
    }

    public static int readInteger() throws NumberFormatException {
        return Integer.parseInt(scanner.nextLine());
    }

    public static LocalDate readDate() throws DateTimeParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(readLine(), dtf);
    }

    public static long inputGuestId() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        long id;
        System.out.println("Введите идентификатор гостя: ");
        id = scanner.nextLong();
        return id;
    }

    public static long inputRoomId() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        long id;
        System.out.println("Введите идентификатор комнаты: ");
        id = scanner.nextLong();
        return id;
    }

    public static long inputMaintenanceId() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        long id;
        System.out.println("Введите идентификатор услуги: ");
        id = scanner.nextLong();
        return id;
    }

    public static RoomStatus inputRoomStatus() {
        int roomStatusIndex = -1;
        RoomStatus[] roomStatuses = RoomStatus.values();

        System.out.println("Выберите текущий статус номера (индекс): ");
        int i = 0;
        while (i < roomStatuses.length) {
            System.out.println(i + 1 + ". " + roomStatuses[i]);
            ++i;
        }
        while (roomStatusIndex < 1 || roomStatusIndex > roomStatuses.length) {
            roomStatusIndex = ConsoleReaderUtil.readInteger();
        }
        return  roomStatuses[roomStatusIndex - 1];
    }

    public static MaintenanceCategory inputMaintenanceCategory() {
        Integer maintenanceCategoryIndex = null;
        MaintenanceCategory[] maintenanceCategories = MaintenanceCategory.values();

        System.out.println("Выберите категорию услуги: ");
        int i = 0;
        while (i < maintenanceCategories.length) {
            System.out.println(i + 1 + ". " + maintenanceCategories[i]);
            ++i;
        }
        do {
            maintenanceCategoryIndex = ConsoleReaderUtil.readInteger();
        } while (maintenanceCategoryIndex < 1 || maintenanceCategoryIndex > maintenanceCategories.length);

        return maintenanceCategories[maintenanceCategoryIndex - 1];
    }
}
