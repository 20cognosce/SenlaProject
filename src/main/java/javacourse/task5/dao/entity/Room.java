package javacourse.task5.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Entity
public class Room extends AbstractEntity {
    @Column(name="capacity")
    private int capacity;
    @Column(name="stars_number")
    private int starsNumber;
    //TODO: прописать логику того, что в данное поле идут только текущие гости
    /*Хранит id гостей из таблицы room_guest
    * */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="room_guest", joinColumns=@JoinColumn(name="room_id"))
    @Column(name="guest_id")
    private final List<Long> currentGuestIdList = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="room_guest", joinColumns=@JoinColumn(name="room_id"))
    @Column(name="guest_id")
    private final List<Long> archivedGuestIdList = new ArrayList<>();
    @Column(name="room_status")
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
    @Column(name="details")
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
