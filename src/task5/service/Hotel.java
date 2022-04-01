package task5.service;

import task5.controller.IAction;
import task5.dao.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Hotel {
    private static final RoomManager roomManager = new RoomManager();
    private static final MaintenanceManager maintenanceManager = new MaintenanceManager();
    private static final GuestManager guestManager = new GuestManager();

    public MaintenanceManager getMaintenanceManager() {
        return maintenanceManager;
    }

    //Guest actions
    public static class AddGuestToDatabaseAction implements IAction {
        private static final AddGuestToDatabaseAction INSTANCE = new AddGuestToDatabaseAction();
        private AddGuestToDatabaseAction(){}

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

            guestManager.addGuest(guestManager.createGuest(fullName, passport, checkInTime, checkOutTime, null));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class AddGuestToRoomAction implements IAction {
        private static final AddGuestToRoomAction INSTANCE = new AddGuestToRoomAction();
        private AddGuestToRoomAction(){}

        @Override
        public void execute() {
            Room room;
            try {
                room = getRoomByInputNumber();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }

            Guest guest;
            try {
                guest = getGuestByInputName();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }
            guestManager.addGuestToRoom(guest, room);
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class RemoveGuestFromDatabaseAction implements IAction {
        private static final RemoveGuestFromDatabaseAction INSTANCE = new RemoveGuestFromDatabaseAction();
        private RemoveGuestFromDatabaseAction(){}

        @Override
        public void execute() {
            Guest guest;
            try {
                guest = getGuestByInputName();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }
            guestManager.removeGuest(guest);
        }
        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class RemoveGuestFromRoomAction implements IAction {
        private static final RemoveGuestFromRoomAction INSTANCE = new RemoveGuestFromRoomAction();
        private RemoveGuestFromRoomAction(){}

        @Override
        public void execute() {
            Guest guest;
            try {
                guest = getGuestByInputName();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }
            guest.setRoom(null);
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintGuestsNumberAction implements IAction {
        private static final PrintGuestsNumberAction INSTANCE = new PrintGuestsNumberAction();
        private PrintGuestsNumberAction(){}

        @Override
        public void execute() {
            System.out.println("Общее число гостей: " + guestManager.getGuests().size());
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintGuestPaymentAction implements IAction {
        private static final PrintGuestPaymentAction INSTANCE = new PrintGuestPaymentAction();
        private PrintGuestPaymentAction(){}

        @Override
        public void execute() {
            Guest guest;
            try {
                guest = getGuestByInputName();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println(guest.getFullName() + "; К оплате: " + guest.getPayment());
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class OrderGuestMaintenanceAction implements IAction {
        private static final OrderGuestMaintenanceAction INSTANCE = new OrderGuestMaintenanceAction();
        private OrderGuestMaintenanceAction(){}

        @Override
        public void execute() {
            Guest guest;
            try {
                guest = getGuestByInputName();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }

            Maintenance maintenance;
            try {
                maintenance = getMaintenanceByInputName();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }

            guest.orderMaintenance(maintenance);
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintGuestsAction implements IAction {
        private static final PrintGuestsAction INSTANCE = new PrintGuestsAction(null);
        protected PrintGuestsAction(Comparator<Guest> comparator){
            this.comparator = comparator;
        }

        private final Comparator<Guest> comparator;

        @Override
        public void execute() {
            if (Objects.isNull(this.comparator)) {
                System.out.println(guestManager.getGuestsAsString(guestManager.getGuests()));
            } else {
                System.out.println(guestManager.getGuestsAsString(
                        guestManager.sortGuests(guestManager.getGuests(), comparator)));
            }
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintGuestsSortedNot extends PrintGuestsAction {
        private static final PrintGuestsSortedNot INSTANCE = new PrintGuestsSortedNot();
        private PrintGuestsSortedNot(){
            super(null);
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintGuestsSortedAlphabet extends PrintGuestsAction {
        private static final PrintGuestsSortedAlphabet INSTANCE = new PrintGuestsSortedAlphabet();
        private PrintGuestsSortedAlphabet(){
            super(Comparator.comparing(Guest::getFullName));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintGuestsSortedByCheckoutDate extends PrintGuestsAction {
        private static final  PrintGuestsSortedByCheckoutDate INSTANCE = new  PrintGuestsSortedByCheckoutDate();
        private  PrintGuestsSortedByCheckoutDate(){
            super(Comparator.comparing(Guest::getCheckOutDate));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintGuestByNameAction implements IAction {
        private static final PrintGuestByNameAction INSTANCE = new PrintGuestByNameAction();
        private PrintGuestByNameAction(){}

        @Override
        public void execute() {
            Guest guest;
            try {
                guest = getGuestByInputName();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println(guestManager.getGuestsAsString(List.of(guest)));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }

    //Room actions
    public static class AddRoomToDatabaseAction implements IAction {
        private static final AddRoomToDatabaseAction INSTANCE = new AddRoomToDatabaseAction();
        private AddRoomToDatabaseAction(){}
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            int capacity;
            int roomNumber = -1;
            int stars;
            int roomStatusIndex = -1;
            int price;

            System.out.println("Введите номер комнаты (number > 0): ");
            while (roomNumber <= 0) {
                roomNumber = scanner.nextInt();
            }

            System.out.println("Введите вместимость номера: ");
            capacity = scanner.nextInt();
            System.out.println("Введите количество звёзд номера: ");
            stars = scanner.nextInt();

            System.out.println("Выберите текущий статус номера (индекс): ");
            ArrayList<RoomStatus> roomStatuses = new ArrayList<>(Arrays.asList(RoomStatus.values()));
            int i = 0;
            while (i < roomStatuses.size()) {
                System.out.println(i + 1 + ". " + roomStatuses.get(i));
                ++i;
            }
            while (roomStatusIndex < 1 || roomStatusIndex > roomStatuses.size()) {
                roomStatusIndex = scanner.nextInt();
            }
            roomStatusIndex--;

            System.out.println("Введите суточную стоимость номера: ");
            price = scanner.nextInt();

            roomManager.addNewRoom(new Room(roomNumber, capacity, stars, roomStatuses.get(roomStatusIndex), price));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class ChangeRoomPriceAction implements IAction {
        private static final ChangeRoomPriceAction INSTANCE = new ChangeRoomPriceAction();
        private ChangeRoomPriceAction(){}
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            int price;

            Room room;
            try {
                room = getRoomByInputNumber();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println("Введите новую стоимость номера: ");
            price = scanner.nextInt();

            room.setPrice(price);
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class ChangeRoomStatusAction implements IAction {
        private static final ChangeRoomStatusAction INSTANCE = new ChangeRoomStatusAction();
        private ChangeRoomStatusAction(){}
        @Override
        public void execute() {
            int roomStatusIndex = -1;
            Room room;
            try {
                room = getRoomByInputNumber();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Выберите текущий статус номера (индекс): ");
            ArrayList<RoomStatus> roomStatuses = new ArrayList<>(Arrays.asList(RoomStatus.values()));
            int i = 0;
            while (i < roomStatuses.size()) {
                System.out.println(i + 1 + ". " + roomStatuses.get(i));
                ++i;
            }
            while (roomStatusIndex < 1 || roomStatusIndex > roomStatuses.size()) {
                roomStatusIndex = scanner.nextInt();
            }
            roomStatusIndex--;

            room.setRoomCurrentStatus(roomStatuses.get(roomStatusIndex));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintRoomDetailsAction implements IAction {
        private static final PrintRoomDetailsAction INSTANCE = new PrintRoomDetailsAction();
        private PrintRoomDetailsAction(){}
        @Override
        public void execute() {
            Room room;
            try {
                room = getRoomByInputNumber();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }

            System.out.println(room.getDetails());
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintNLastGuestsAction implements IAction {
        private static final PrintNLastGuestsAction INSTANCE = new PrintNLastGuestsAction();
        private PrintNLastGuestsAction(){}
        @Override
        public void execute() {
            Room room;
            try {
                room = getRoomByInputNumber();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }

            Scanner scanner = new Scanner(System.in);
            int number;
            System.out.println("Введите количество последних гостей номера для показа информации: ");
            number = scanner.nextInt();
            System.out.println(guestManager.getGuestsAsString(room.getLastNGuests(number)));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintFreeRoomsNumberAction implements IAction {
        private static final PrintFreeRoomsNumberAction INSTANCE = new PrintFreeRoomsNumberAction();
        private PrintFreeRoomsNumberAction(){}
        @Override
        public void execute() {
            System.out.println("Количество свободных комнат на данный момент: " + roomManager.getFreeRooms().size());
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintFreeRoomsAction implements IAction {
        private static final PrintFreeRoomsAction INSTANCE = new PrintFreeRoomsAction(null);
        protected PrintFreeRoomsAction(Comparator<Room> comparator){
            this.comparator = comparator;
        }

        Comparator<Room> comparator;

        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            LocalDate specificDate;
            System.out.println("Введите на какую дату свободные номера вас интересуют [dd.MM.yyyy]: ");
            String str = scanner.nextLine();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            specificDate = LocalDate.parse(str, dtf);
            if (Objects.isNull(comparator)) {
                System.out.println(roomManager.getRoomsAsString(roomManager.getFreeRooms(specificDate)));
            } else {
                System.out.println(roomManager.getRoomsAsString(
                        roomManager.sortRooms(roomManager.getFreeRooms(specificDate), comparator)));
            }
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintFreeRoomsSortedNot extends PrintFreeRoomsAction {
        private static final PrintFreeRoomsSortedNot INSTANCE = new PrintFreeRoomsSortedNot();
        private PrintFreeRoomsSortedNot(){
            super(null);
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintFreeRoomsSortedByPrice extends PrintFreeRoomsAction {
        private static final PrintFreeRoomsSortedByPrice INSTANCE = new PrintFreeRoomsSortedByPrice();
        private PrintFreeRoomsSortedByPrice(){
            super(Comparator.comparingInt(Room::getPrice));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintFreeRoomsSortedByCapacity extends PrintFreeRoomsAction {
        private static final PrintFreeRoomsSortedByCapacity INSTANCE = new PrintFreeRoomsSortedByCapacity();
        private PrintFreeRoomsSortedByCapacity(){
            super(Comparator.comparingInt(Room::getCapacity));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintFreeRoomsSortedByStars extends PrintFreeRoomsAction {
        private static final PrintFreeRoomsSortedByStars INSTANCE = new PrintFreeRoomsSortedByStars();
        private PrintFreeRoomsSortedByStars(){
            super(Comparator.comparingInt(Room::getStarsNumber));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintRoomsAction implements IAction {
        private static final PrintRoomsAction INSTANCE = new PrintRoomsAction(null);
        protected PrintRoomsAction(Comparator<Room> comparator){
            this.comparator = comparator;
        }

        private final Comparator<Room> comparator;

        @Override
        public void execute() {
            if (Objects.isNull(this.comparator)) {
            System.out.println(roomManager.getRoomsAsString());
            } else {
                System.out.println(roomManager.getRoomsAsString(roomManager.sortRooms(roomManager.getRooms(), comparator)));
            }
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintRoomsSortedNot extends PrintRoomsAction {
        private static final PrintRoomsSortedNot INSTANCE = new PrintRoomsSortedNot();
        private PrintRoomsSortedNot(){
            super(null);
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintRoomsSortedByPrice extends PrintRoomsAction {
        private static final PrintRoomsSortedByPrice INSTANCE = new PrintRoomsSortedByPrice();
        private PrintRoomsSortedByPrice(){
            super(Comparator.comparingInt(Room::getPrice));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintRoomsSortedByCapacity extends PrintRoomsAction {
        private static final  PrintRoomsSortedByCapacity INSTANCE = new  PrintRoomsSortedByCapacity();
        private  PrintRoomsSortedByCapacity(){
            super(Comparator.comparingInt(Room::getCapacity));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintRoomsSortedByStars extends PrintRoomsAction {
        private static final  PrintRoomsSortedByStars INSTANCE = new  PrintRoomsSortedByStars();
        private  PrintRoomsSortedByStars(){
            super(Comparator.comparingInt(Room::getStarsNumber));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintRoomByNumberAction implements IAction {
        private static final PrintRoomByNumberAction INSTANCE = new PrintRoomByNumberAction();
        private PrintRoomByNumberAction(){}
        @Override
        public void execute() {
            Room room;
            try {
                room = getRoomByInputNumber();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println(room.toString() + "Текущий список гостей:\n"
                    + guestManager.getGuestsAsString(room.getGuestsCurrentList()));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }

    //Maintenance actions
    public static class AddMaintenanceAction implements IAction {
        private static final AddMaintenanceAction INSTANCE = new AddMaintenanceAction();
        private AddMaintenanceAction(){}
        @Override
        public void execute() {
            String name;
            int maintenanceCategoryIndex = -1;
            int price;

            Scanner scanner = new Scanner(System.in);

            System.out.println("Введите наименование услуги: ");
            name = scanner.nextLine();

            System.out.println("Введите цену: ");
            price = scanner.nextInt();

            System.out.println("Выберите текущий статус номера (индекс): ");
            ArrayList<MaintenanceCategory> maintenanceCategories = new ArrayList<>(Arrays.asList(MaintenanceCategory.values()));
            int i = 0;
            while (i < maintenanceCategories.size()) {
                System.out.println(i + 1 + ". " + maintenanceCategories.get(i));
                ++i;
            }
            while (maintenanceCategoryIndex < 1 || maintenanceCategoryIndex > maintenanceCategories.size()) {
                maintenanceCategoryIndex = scanner.nextInt();
            }
            maintenanceCategoryIndex--;

            maintenanceManager.addNewMaintenance(new Maintenance(name, price, maintenanceCategories.get(maintenanceCategoryIndex)));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class ChangeMaintenancePriceAction implements IAction {
        private static final ChangeMaintenancePriceAction INSTANCE = new ChangeMaintenancePriceAction();
        private ChangeMaintenancePriceAction(){}
        @Override
        public void execute() {
            Maintenance maintenance;
            int price;
            try {
                maintenance = getMaintenanceByInputName();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите новую стоимость услуги: ");
            price = scanner.nextInt();
            maintenance.setPrice(price);
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintMaintenancesAction implements IAction {
        private static final PrintMaintenancesOfGuestAction INSTANCE = new PrintMaintenancesOfGuestAction(null);
        protected PrintMaintenancesAction(Comparator<Maintenance> comparator) {
            this.comparator = comparator;
        }

        private final Comparator<Maintenance> comparator;
        @Override
        public void execute() {
            if (Objects.isNull(comparator)) {
                System.out.println(maintenanceManager.getMaintenancesPriceList());
            } else {
                System.out.println(maintenanceManager.getMaintenancesPriceList(comparator));
            }
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintMaintenancesSortedNot extends PrintMaintenancesAction {
        private static final PrintMaintenancesSortedNot INSTANCE = new PrintMaintenancesSortedNot();
        private PrintMaintenancesSortedNot(){
            super(null);
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintMaintenancesSortedByPrice extends PrintMaintenancesAction {
        private static final PrintMaintenancesSortedByPrice INSTANCE = new PrintMaintenancesSortedByPrice();
        private PrintMaintenancesSortedByPrice(){
            super(Comparator.comparing(Maintenance::getPrice));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintMaintenancesSortedByCategory extends PrintMaintenancesAction {
        private static final PrintMaintenancesSortedByCategory INSTANCE = new PrintMaintenancesSortedByCategory();
        private PrintMaintenancesSortedByCategory(){
            super(Comparator.comparing(Maintenance::getCategory));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintMaintenancesOfGuestAction implements IAction {
        private static final PrintMaintenancesOfGuestAction INSTANCE = new PrintMaintenancesOfGuestAction(null);
        protected PrintMaintenancesOfGuestAction(Comparator<Maintenance> comparator) {
            this.comparator = comparator;
        }

        private final Comparator<Maintenance> comparator;

        @Override
        public void execute() {
            Guest guest;
            try {
                guest = getGuestByInputName();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }

            if (Objects.isNull(comparator)) {
                System.out.println(guest.getOrderedMaintenancesAsString());
            } else {
                System.out.println(maintenanceManager.getMaintenancesOfGuest(guest, comparator));
            }
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintMaintenancesOfGuestSortedNot extends PrintMaintenancesOfGuestAction {
        private static final PrintMaintenancesOfGuestSortedNot INSTANCE = new PrintMaintenancesOfGuestSortedNot();
        private PrintMaintenancesOfGuestSortedNot(){
            super(null);
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintMaintenancesOfGuestSortedByPrice extends PrintMaintenancesOfGuestAction {
        private static final PrintMaintenancesOfGuestSortedByPrice INSTANCE = new PrintMaintenancesOfGuestSortedByPrice();
        private PrintMaintenancesOfGuestSortedByPrice(){
            super(Comparator.comparing(Maintenance::getPrice));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintMaintenancesOfGuestSortedByTime extends PrintMaintenancesOfGuestAction {
        private static final PrintMaintenancesOfGuestSortedByTime INSTANCE = new PrintMaintenancesOfGuestSortedByTime();
        private PrintMaintenancesOfGuestSortedByTime(){
            super(Comparator.comparing(Maintenance::getOrderTime));
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }
    public static class PrintMaintenanceByNameAction implements IAction {
        private static final  PrintMaintenanceByNameAction INSTANCE = new  PrintMaintenanceByNameAction();
        private  PrintMaintenanceByNameAction(){}
        @Override
        public void execute() {
            Maintenance maintenance;
            try {
                maintenance = getMaintenanceByInputName();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println(maintenance.toString());
        }

        public static IAction getInstance() {
            return INSTANCE;
        }
    }

    private static Guest getGuestByInputName() throws NoSuchElementException {
        Scanner scanner = new Scanner(System.in);
        String name;
        System.out.println("Введите полное имя гостя: ");
        name = scanner.nextLine();
        return guestManager.getGuestByName(name);
    }
    private static Room getRoomByInputNumber() throws NoSuchElementException {
        Scanner scanner = new Scanner(System.in);
        int number;
        System.out.println("Введите номер комнаты: ");
        number = scanner.nextInt();
        return roomManager.getRoomByNumber(number);
    }
    private static Maintenance getMaintenanceByInputName() throws NoSuchElementException {
        Scanner scanner = new Scanner(System.in);
        String name;
        System.out.println("Введите название услуги: ");
        name = scanner.nextLine();
        return maintenanceManager.getMaintenanceByName(name);
    }
}















