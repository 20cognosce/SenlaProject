package task3.prog4;

import java.util.ArrayList;

public class Room {
    private final int GUESTS_MAX_NUMBER;
    private final int ROOM_NUMBER;
    private final ArrayList<Guest> currentGuestsList;
    private RoomStatus roomCurrentStatus;
    private int price;

    public Room(int guestsMaxNumber, int roomNumber, RoomStatus roomCurrentStatus, int price) {
        this.ROOM_NUMBER = roomNumber;
        this.GUESTS_MAX_NUMBER = guestsMaxNumber;
        this.roomCurrentStatus = roomCurrentStatus;
        this.price = price;
        currentGuestsList = new ArrayList<>(guestsMaxNumber);
    }

    public int getGuestsMaxNumber() {
        return GUESTS_MAX_NUMBER;
    }

    public int getRoomNumber() {
        return ROOM_NUMBER;
    }

    public RoomStatus getRoomCurrentStatus() {
        return roomCurrentStatus;
    }

    public ArrayList<Guest> getCurrentGuestsList() {
        return currentGuestsList;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRoomCurrentStatus(RoomStatus roomCurrentStatus) {
        this.roomCurrentStatus = roomCurrentStatus;
    }

    void addGuest(Guest guest) {
        if (getRoomCurrentStatus() == RoomStatus.UNDER_REPAIR || getRoomCurrentStatus() == RoomStatus.CLEANING) {
            System.out.println("Room status does not allow to settle guests now");
            return;
        }
        if (getGuestsMaxNumber() <= getCurrentGuestsList().size()) {
            System.out.println("Unable to settle guest " + guest.getFullName()
                    + " in that room. Max guests number is: " + GUESTS_MAX_NUMBER);
            return;
        }
        currentGuestsList.add(guest);
    }

    void removeGuest(Guest guest) {
        currentGuestsList.remove(guest);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Список гостей в номере " + this.getRoomNumber() + ":\n");
        currentGuestsList.forEach(guest -> out.append(guest.getFullName()).append("\n"));
        return out.toString();
    }
}
