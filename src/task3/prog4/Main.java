package task3.prog4;

//Variant 1 - online hotel administrator

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.loadRoomsDatabase();
        hotel.loadServicesDatabase();

        Guest guest1 = new Guest("Ivanov Ivan Ivanovich", "1111 222333");
        Guest guest2 = new Guest("Ivanova Maria Ivanovna", "4444 555666");
        Guest guest3 = new Guest("Petrov Petr Petrovich", "7777 888999");

        Room bookedRoom1 = hotel.getRoomByNumber(1);
        bookedRoom1.addGuest(guest1);
        bookedRoom1.addGuest(guest2);
        bookedRoom1.addGuest(guest3);

        System.out.println(bookedRoom1);
        guest1.orderService(hotel, "Завтрак в номер");
        System.out.println();

        Room BookedRoom2 =  hotel.getRoomByNumber(3);
        BookedRoom2.addGuest(guest3);
        BookedRoom2.setRoomCurrentStatus(RoomStatus.FREE);
        BookedRoom2.addGuest(guest3);
        System.out.println(BookedRoom2);

        bookedRoom1.removeGuest(guest1);
        System.out.println(bookedRoom1);
    }
}
