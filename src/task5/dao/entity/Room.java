package task5.dao.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static task5.dao.entity.RoomStatus.BUSY;
import static task5.dao.entity.RoomStatus.FREE;

public class Room extends AbstractEntity {
    private int capacity;
    private int starsNumber;
    private final List<Guest> guestsCurrentList;
    private final List<Guest> guestsAllTimeList;
    private RoomStatus roomStatus;
    private String details;

    public Room(long id, String name, int guestsMaxNumber, int starsNumber, RoomStatus roomStatus, int price) {
        super(id, name, price);
        this.capacity = guestsMaxNumber;
        this.starsNumber = starsNumber;
        this.roomStatus = roomStatus;
        guestsCurrentList = new ArrayList<>(guestsMaxNumber);
        guestsAllTimeList = new ArrayList<>();
        details = null;
    }

    public void addGuest(Guest guest) throws RuntimeException {
        guestsCurrentList.add(guest);
        guestsAllTimeList.add(guest);
        setRoomStatus(BUSY);
    }

    public void removeGuest(Guest guest) throws NoSuchElementException {
        if(!guestsCurrentList.remove(guest)) {
            throw new NoSuchElementException("Such guest does not exist in that room");
        }
        if (getGuestsCurrentList().isEmpty()) setRoomStatus(FREE);
    }

    public List<Guest> getGuestsCurrentList() {
        return new ArrayList<>(guestsCurrentList);
    }

    public List<Guest> getGuestsAllTimeList() {
        return new ArrayList<>(guestsAllTimeList);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getStarsNumber() {
        return starsNumber;
    }

    public void setStarsNumber(int starsNumber) {
        this.starsNumber = starsNumber;
    }

    public String getDetails() {
        if (Objects.isNull(details)) {
            return "Детали номера " + getName() + ": " + "без деталей";
        }
        return "Детали номера " + getName() + ": " + details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    @Override
    public String toString() {
        return "id: " + getId() +
                "; Номер: " + getName() +
                "; Вместимость: " + getCapacity() +
                "; Звёзды: " + getStarsNumber() +
                "; Статус: " + getRoomStatus() +
                "; Цена: " + getPrice() +
                "\n";
    }
}
