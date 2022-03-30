package task5.service;

import task5.controller.IAction;
import task5.dao.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Hotel {
    private static final RoomManager roomManager = new RoomManager();
    private static final MaintenanceManager maintenanceManager = new MaintenanceManager();
    private static final GuestManager guestManager = new GuestManager();

    public MaintenanceManager getMaintenanceManager() {
        return maintenanceManager;
    }

    //Guest actions
    public static class AddGuestToDatabaseAction implements IAction {
        private static final AddGuestToDatabaseAction addGuestToDatabaseAction_INSTANCE = new AddGuestToDatabaseAction();
        private AddGuestToDatabaseAction(){};

        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            String str;
            DateTimeFormatter dtf;

            String fullName;
            System.out.println("Введите полное имя гостя: ");
            fullName = scanner.nextLine();

            String passport;
            System.out.println("Введите паспорт гостя: ");
            passport = scanner.nextLine();

            LocalDate checkInTime;
            System.out.println("Введите дату заезда гостя [dd.MM.yyyy]: ");
            str = scanner.nextLine();
            dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            checkInTime = LocalDate.parse(str, dtf);

            LocalDate checkOutTime;
            System.out.println("Введите дату выезда гостя [dd.MM.yyyy]: ");
            str = scanner.nextLine();
            dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            checkOutTime = LocalDate.parse(str, dtf);

            int roomNumber;
            System.out.println("Введите номер комнаты гостя (0 если оставить без комнаты): ");
            roomNumber = scanner.nextInt();
            Room room;
            if (roomNumber == 0 ){
                room = null;
            } else {
                room = roomManager.getRoomByNumber(roomNumber);
            }

            guestManager.addGuest(guestManager.createGuest(fullName, passport, checkInTime, checkOutTime, room));
        }

        public static IAction getInstance() {
            return addGuestToDatabaseAction_INSTANCE;
        }
    }
    public static class AddGuestToRoomAction implements IAction {
        private static final AddGuestToRoomAction addGuestToRoomAction_INSTANCE = new AddGuestToRoomAction();
        private AddGuestToRoomAction(){};

        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            String name;
            int roomNumber;

            System.out.println("Введите полное имя гостя: ");
            name = scanner.nextLine();

            System.out.println("Введите номер комнаты: ");
            roomNumber = scanner.nextInt();

            guestManager.addGuestToRoom(guestManager.getGuestByName(name), roomManager.getRoomByNumber(roomNumber));
        }

        public static IAction getInstance() {
            return addGuestToRoomAction_INSTANCE;
        }
    }
    public static class RemoveGuestFromDatabaseAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class RemoveGuestFromRoomAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class PrintGuestsNumberAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class PrintGuestPaymentAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class PrintGuestMaintenancesAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class PrintGuestsAction implements IAction {
        private static final PrintGuestsAction printGuestsAction_INSTANCE = new PrintGuestsAction();
        private PrintGuestsAction(){};

        @Override
        public void execute() {
            System.out.println(guestManager.getGuestsAsString(guestManager.getGuests()));
        }

        public static IAction getInstance() {
            return printGuestsAction_INSTANCE;
        }
    }
    public static class PrintGuestByNameAction implements IAction {
        @Override
        public void execute() {

        }
    }

    //Room actions
    public static class AddRoomToDatabaseAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class ChangeRoomPriceAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class ChangeRoomStatusAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class PrintRoomDetailsAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class PrintNLastGuestsAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class PrintRoomsForSpecificDateAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class PrintRoomsAction implements IAction {
        private static final PrintRoomsAction printRoomsAction_INSTANCE = new PrintRoomsAction();
        private PrintRoomsAction(){};

        @Override
        public void execute() {
            System.out.println(roomManager.getRoomsAsString());
        }

        public static IAction getInstance() {
            return printRoomsAction_INSTANCE;
        }
    }
    public static class PrintFreeRoomsAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class PrintRoomByNumberAction implements IAction {
        @Override
        public void execute() {

        }
    }

    //Maintenance actions
    public static class AddMaintenanceAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class ChangeMaintenancePriceAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class PrintMaintenancesAction implements IAction {
        @Override
        public void execute() {

        }
    }
    public static class PrintMaintenanceByNameAction implements IAction {
        @Override
        public void execute() {

        }
    }
}















