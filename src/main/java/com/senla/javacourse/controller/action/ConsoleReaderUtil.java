package com.senla.javacourse.controller.action;

import com.senla.javacourse.dao.entity.MaintenanceCategory;
import com.senla.javacourse.dao.entity.RoomStatus;

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

    public static long inputId(String message) throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        long id;
        System.out.println(message);
        id = scanner.nextLong();
        return id;
    }

    public static RoomStatus inputRoomStatus() {
        int roomStatusIndex;
        RoomStatus[] roomStatuses = RoomStatus.values();

        System.out.println("Выберите текущий статус номера (индекс): ");
        int i = 0;
        while (i < roomStatuses.length) {
            System.out.println(i + 1 + ". " + roomStatuses[i]);
            ++i;
        }
        do {
            roomStatusIndex = ConsoleReaderUtil.readInteger();
        } while (roomStatusIndex < 1 || roomStatusIndex > roomStatuses.length);

        return  roomStatuses[roomStatusIndex - 1];
    }

    public static MaintenanceCategory inputMaintenanceCategory() {
        int maintenanceCategoryIndex;
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
