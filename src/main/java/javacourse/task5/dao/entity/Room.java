package javacourse.task5.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Room extends AbstractEntity {
    private int capacity;
    private int starsNumber;
    private final List<Long> currentGuestIdList = new ArrayList<>();
    private final List<Long> archivedGuestIdList = new ArrayList<>();
    private RoomStatus roomStatus;
    private String details;

    //new Room constructor
    public Room(long id, String name, int capacity, int starsNumber, RoomStatus roomStatus, int price) {
        super(id, name, price);
        this.capacity = capacity;
        this.starsNumber = starsNumber;
        this.roomStatus = roomStatus;
        this.details = "";
    }

    //total constructor
    public Room(long id, String name, int price, int capacity, int starsNumber, RoomStatus roomStatus, String details,
                List<Long> currentGuestIdList, List<Long> archivedGuestIdList) {
        super(id, name, price);
        this.capacity = capacity;
        this.starsNumber = starsNumber;
        this.roomStatus = roomStatus;
        this.details = details;
        this.currentGuestIdList.addAll(currentGuestIdList);
        this.archivedGuestIdList.addAll(archivedGuestIdList);
    }

    public Room() {
        super(0, "", 0);
    }

    public void addGuest(Guest guest) throws RuntimeException {
        currentGuestIdList.add(guest.getId());
        setRoomStatus(RoomStatus.BUSY);
    }

    public void removeGuest(long guestId) throws NoSuchElementException {
        if (!currentGuestIdList.remove(guestId)) {
            throw new NoSuchElementException("Such guest does not exist in that room");
        }
        archivedGuestIdList.add(guestId);
        if (getCurrentGuestIdList().isEmpty()) setRoomStatus(RoomStatus.FREE);
    }

    public List<Long> getCurrentGuestIdList() {
        return new ArrayList<>(currentGuestIdList);
    }

    public List<Long> getArchivedGuestIdList() {
        return new ArrayList<>(archivedGuestIdList);
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
            return "без деталей";
        }
        return details;
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

    @JsonIgnore
    public boolean isUnavailableToSettle() {
        return (this.getRoomStatus() != RoomStatus.FREE && this.getRoomStatus() != RoomStatus.BUSY)
                || (getCurrentGuestIdList().size() >= getCapacity());
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
