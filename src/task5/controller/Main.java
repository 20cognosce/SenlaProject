package task5.controller;

import task5.dao.Guest;
import task5.dao.Maintenance;
import task5.dao.Room;
import task5.service.Hotel;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.roomManager.loadRoomsDatabase();
        hotel.maintenanceManager.loadMaintenancesDatabase();
        hotel.guestManager.loadGuestsDatabase();

        // Список свободных номеров (сортировать по цене, вместимости, количеству звезд);
        System.out.println(hotel.roomManager.getRoomsAsString(hotel.roomManager.getFreeRooms()));
        System.out.println(hotel.roomManager.getRoomsAsString(hotel.roomManager.sortRooms(
                hotel.roomManager.getFreeRooms(), Comparator.comparingInt(Room::getPrice))));
        System.out.println(hotel.roomManager.getRoomsAsString(hotel.roomManager.sortRooms(
                hotel.roomManager.getFreeRooms(), Comparator.comparingInt(Room::getCapacity))));
        System.out.println(hotel.roomManager.getRoomsAsString(hotel.roomManager.sortRooms(
                hotel.roomManager.getFreeRooms(), Comparator.comparingInt(Room::getStarsNumber)))
                + "--------------------------------------------------------------------------------------------------");

        Room bookedRoom1 = hotel.roomManager.getRoomByNumber(1);
        Guest guest1 = hotel.guestManager.getGuestByName("Ivanov Ivan Ivanovich");
        Guest guest2 = hotel.guestManager.getGuestByName("Ivanova Maria Ivanovna");
        hotel.guestManager.addGuestToRoom(bookedRoom1, guest1);
        hotel.guestManager.addGuestToRoom(bookedRoom1, guest2);
        guest1.setPayment(bookedRoom1.getPrice());
        Room bookedRoom2 = hotel.roomManager.getRoomByNumber(2);
        Guest guest3 = hotel.guestManager.getGuestByName("Petrov Petr Petrovich");
        Guest guest4 = hotel.guestManager.getGuestByName("Yakovleva Margarita Vladimirovna");
        hotel.guestManager.addGuestToRoom(bookedRoom2, guest3);
        hotel.guestManager.addGuestToRoom(bookedRoom2, guest4);
        guest3.setPayment(bookedRoom2.getPrice());
        Room bookedRoom5 = hotel.roomManager.getRoomByNumber(5);
        Guest guest5 = hotel.guestManager.getGuestByName("Abramov Nikita Alexandrovich");
        hotel.guestManager.addGuestToRoom(bookedRoom5, guest5);
        guest5.setPayment(bookedRoom5.getPrice());

        // Список номеров (сортировать по цене, вместимости, количеству звезд);
        System.out.println(hotel.roomManager.getRoomsAsString(hotel.roomManager.getRooms()));
        System.out.println(hotel.roomManager.getRoomsAsString(hotel.roomManager.sortRooms(
                hotel.roomManager.getRooms(), Comparator.comparingInt(Room::getPrice))));
        System.out.println(hotel.roomManager.getRoomsAsString(hotel.roomManager.sortRooms(
                hotel.roomManager.getRooms(), Comparator.comparingInt(Room::getCapacity))));
        System.out.println(hotel.roomManager.getRoomsAsString(hotel.roomManager.sortRooms(
                hotel.roomManager.getRooms(), Comparator.comparingInt(Room::getStarsNumber)))
                + "--------------------------------------------------------------------------------------------------");

        //Список постояльцев и их номеров (сортировать по алфавиту, дате освобождения номера);
        System.out.println(hotel.guestManager.getGuestsAsString(
                hotel.guestManager.getGuests()));
        System.out.println(hotel.guestManager.getGuestsAsString(
                hotel.guestManager.sortGuests(
                hotel.guestManager.getGuests(), Comparator.comparing(Guest::getFullName))));
        System.out.println(hotel.guestManager.getGuestsAsString(
                hotel.guestManager.sortGuests(
                hotel.guestManager.getGuests(), Comparator.comparing(Guest::getCheckOutTime)))
                + "--------------------------------------------------------------------------------------------------");

        // Общее число свободных номеров; Общее число постояльцев;
        System.out.println("Общее число свободных номеров: " + hotel.roomManager.getFreeRooms().size());
        System.out.println("Общее число постояльцев: " + hotel.guestManager.getGuests().size() + "\n"
                + "--------------------------------------------------------------------------------------------------");

        // Список номеров которые будут свободны по определенной дате в будущем;
        LocalDateTime searchForDate = LocalDateTime.of(2022, 3, 10, 14, 0);
        System.out.println("Свободные номера на " + searchForDate + "\n" +
                hotel.roomManager.getRoomsAsString(
                hotel.roomManager.getFreeRooms(searchForDate))
                + "--------------------------------------------------------------------------------------------------"
        );

        // Посмотреть 3-х последних постояльцев номера и даты их пребывания;
        hotel.roomManager.getRoomByNumber(1).getLastNGuests(3).forEach(
                guest -> System.out.println(guest.getFullName()
                        + "; Дата въезда: " + guest.getCheckInTime()
                        + "; Дата выезда: " + guest.getCheckOutTime())
        );
        System.out.println(
                "--------------------------------------------------------------------------------------------------");

        // Цены услуг и номеров (сортировать по разделу, цене);
        hotel.maintenanceManager.getMaintenancesPriceList();
        System.out.println(hotel.maintenanceManager.getMaintenancesPriceList());
        System.out.println(hotel.maintenanceManager.getMaintenancesPriceList(Comparator.comparing(Maintenance::getPrice)));
        System.out.println(hotel.maintenanceManager.getMaintenancesPriceList(Comparator.comparing(Maintenance::getCategory))
                + "--------------------------------------------------------------------------------------------------");

        // Посмотреть список услуг постояльца и их цену (сортировать по цене, по дате);
        guest1.orderMaintenance(hotel, "Завтрак в номер");
        guest1.orderMaintenance(hotel, "Завтрак в номер");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        guest1.orderMaintenance(hotel, "Обед в номер");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        guest1.orderMaintenance(hotel, "Принести доставку в номер");
        System.out.println();

        System.out.println(hotel.maintenanceManager
                .getMaintenancesOfGuest(guest1, Comparator.comparing(Maintenance::getOrderTime).reversed()));
        System.out.println(hotel.maintenanceManager
                .getMaintenancesOfGuest(guest1, Comparator.comparing(Maintenance::getPrice))
                + "--------------------------------------------------------------------------------------------------");

        // Сумму оплаты за номер которую должен оплатить постоялец;
        // Цены заказанных услуг входят в сумму оплаты за номер (Guest::orderMaintenance method)
        System.out.println("Счёт для гостя " + guest1.getFullName()
                + ": " + guest1.getPayment()
                + "\nСтоимость номера: " + guest1.getRoom().getPrice() + "\n"
                + "--------------------------------------------------------------------------------------------------");

        // Посмотреть детали отдельного номера.
        System.out.println(bookedRoom5.getDetails());

    }
}
