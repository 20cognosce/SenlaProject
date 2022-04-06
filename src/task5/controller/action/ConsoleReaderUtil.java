package task5.controller.action;

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

    public static int inputGuestId() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        int id;
        System.out.println("Введите идентификатор гостя: ");
        id = scanner.nextInt();
        return id;
    }

    public static int inputRoomId() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        int id;
        System.out.println("Введите идентификатор комнаты: ");
        id = scanner.nextInt();
        return id;
    }

    public static int inputMaintenanceId() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        int id;
        System.out.println("Введите идентификатор услуги: ");
        id = scanner.nextInt();
        return id;
    }
}
