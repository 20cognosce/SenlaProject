package task4.prog1;

import java.util.ArrayList;

import static task4.prog1.RoomStatus.*;

public class Room {
    private final int CAPACITY;
    private final int ROOM_NUMBER;
    private final int STARS_NUMBER;
    private final ArrayList<Guest> guestsCurrentList;
    private final ArrayList<Guest> guestsAllTimeList;
    private RoomStatus roomCurrentStatus;
    private int price;
    private String details;

    public Room(int roomNumber, int guestsMaxNumber, int STARS_NUMBER, RoomStatus roomCurrentStatus, int price) {
        this.ROOM_NUMBER = roomNumber;
        this.CAPACITY = guestsMaxNumber;
        this.STARS_NUMBER = STARS_NUMBER;
        this.roomCurrentStatus = roomCurrentStatus;
        this.price = price;
        guestsCurrentList = new ArrayList<>(guestsMaxNumber);
        guestsAllTimeList = new ArrayList<>();
    }

    public int getCapacity() {
        return CAPACITY;
    }

    public int getRoomNumber() {
        return ROOM_NUMBER;
    }

    public RoomStatus getRoomCurrentStatus() {
        return roomCurrentStatus;
    }

    public ArrayList<Guest> getGuestsCurrentList() {
        return guestsCurrentList;
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

    void addGuest(Guest guest) throws RuntimeException {
        if (getRoomCurrentStatus().equals(RoomStatus.UNDER_REPAIR) || getRoomCurrentStatus().equals(RoomStatus.CLEANING)) {
            throw new RuntimeException("Room status does not allow to settle guests now");
        }
        if (getCapacity() <= getGuestsCurrentList().size()) {
            throw new RuntimeException("Unable to settle guest " + guest.getFullName()
                    + " in that room. Max guests number is: " + CAPACITY);
        }
        guestsCurrentList.add(guest);
        guestsAllTimeList.add(guest);
        setRoomCurrentStatus(BUSY);
    }

    void removeGuest(Guest guest) throws RuntimeException {
        if(!guestsCurrentList.remove(guest)) {
            throw new RuntimeException("No such guest in this room: "
                    + guest.getFullName() + "; Room " + getRoomNumber());
        }
        if (getGuestsCurrentList().isEmpty()) setRoomCurrentStatus(FREE);
    }

    public String getGuestsListAsString(ArrayList<Guest> guestList) {
        StringBuilder out = new StringBuilder("Список гостей в номере " + this.getRoomNumber() + ":\n");
        guestList.forEach(guest -> out.append(guest.getFullName()).append("\n"));
        return out.toString();
    }

    @Override
    public String toString() {
        return "Номер " + this.getRoomNumber() + " : " + "Цена: " + getPrice() + "; Вместимость: " +
                getCapacity() + "; Количество звёзд: " +
                getStarsNumber() + "; Текущий статус: " + getRoomCurrentStatus() + "\n";
    }

    public int getStarsNumber() {
        return STARS_NUMBER;
    }

    public String getDetails() {
        return "Детали номера " + getRoomNumber() + ": " + details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ArrayList<Guest> getLastNGuests(int N) {
        int lastGuestIndex = guestsAllTimeList.size() - 1;
        ArrayList<Guest> out = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            try {
                out.add(guestsAllTimeList.get(lastGuestIndex - i));
            } catch (RuntimeException e) {
                break;
            }
        }
        return out;
    }
}
