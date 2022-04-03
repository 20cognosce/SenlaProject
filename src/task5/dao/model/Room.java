package task5.dao.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static task5.dao.model.RoomStatus.*;

public class Room {
    private final int ID;
    private final int CAPACITY;
    private final int ROOM_NUMBER;
    private final int STARS_NUMBER;
    private final List<Guest> guestsCurrentList;
    private final List<Guest> guestsAllTimeList;
    private RoomStatus roomCurrentStatus;
    private int price;
    private String details;

    public Room(int ID, int roomNumber, int guestsMaxNumber, int STARS_NUMBER, RoomStatus roomCurrentStatus, int price) {
        this.ID = ID;
        this.ROOM_NUMBER = roomNumber;
        this.CAPACITY = guestsMaxNumber;
        this.STARS_NUMBER = STARS_NUMBER;
        this.roomCurrentStatus = roomCurrentStatus;
        this.price = price;
        guestsCurrentList = new ArrayList<>(guestsMaxNumber);
        guestsAllTimeList = new ArrayList<>();
    }

    public List<Guest> getGuestsCurrentList() {
        return new ArrayList<>(guestsCurrentList);
    }

    public List<Guest> getGuestsAllTimeList() {
        return new ArrayList<>(guestsAllTimeList);
    }

    public void addGuest(Guest guest) throws RuntimeException {
        if (getRoomCurrentStatus() == RoomStatus.UNDER_REPAIR || getRoomCurrentStatus() == RoomStatus.CLEANING) {
            throw new RuntimeException("Inappropriate room status");
        }
        if (getCapacity() <= getGuestsCurrentList().size()) {
            throw new RuntimeException("Room's capacity limit is exceeded");
        }
        guestsCurrentList.add(guest);
        guestsAllTimeList.add(guest);
        setRoomCurrentStatus(BUSY);
    }

    public void removeGuest(Guest guest) throws NoSuchElementException {
        if(!guestsCurrentList.remove(guest)) {
            throw new NoSuchElementException("Such guest does not exist in that room");
        }
        if (getGuestsCurrentList().isEmpty()) setRoomCurrentStatus(FREE);
    }

    public String getGuestsListAsString(List<Guest> guestList) {
        StringBuilder out = new StringBuilder("Список гостей в номере " + this.getRoomNumber() + ":\n");
        guestList.forEach(guest -> out.append(guest.getFullName()).append("\n"));
        return out.toString();
    }

    public int getId() {
        return ID;
    }

    public int getCapacity() {
        return CAPACITY;
    }

    public int getRoomNumber() {
        return ROOM_NUMBER;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRoomCurrentStatus(RoomStatus roomCurrentStatus) {
        this.roomCurrentStatus = roomCurrentStatus;
    }

    public RoomStatus getRoomCurrentStatus() {
        return roomCurrentStatus;
    }

    @Override
    public String toString() {
        return "Номер " + this.getRoomNumber() + " : " + "Цена: " + getPrice() + "; Вместимость: " +
                getCapacity() + "; Количество звёзд: " +
                getStarsNumber() + "; Текущий статус: " + getRoomCurrentStatus() + "\n";
    }
}
