package javacourse.task5.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

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

@Entity
public class Room extends AbstractEntity {
    @Getter
    @Setter
    @Column(name="capacity")
    private int capacity;
    @Getter
    @Setter
    @Column(name="stars_number")
    private int starsNumber;
    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="room_guest", joinColumns=@JoinColumn(name="room_id"))
    @Column(name="guest_id")
    private final List<Long> currentGuestIdList = new ArrayList<>();
    @Getter
    @Setter
    @Column(name="room_status")
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
    @Getter
    @Setter
    @Column(name="details")
    private String details;

    //new Room constructor
    public Room(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price) {
        super(name, price);
        this.capacity = capacity;
        this.starsNumber = starsNumber;
        this.roomStatus = roomStatus;
        this.details = "";
    }

    //total constructor
    public Room(String name, int price, int capacity, int starsNumber, RoomStatus roomStatus, String details,
                List<Long> currentGuestIdList) {
        super(name, price);
        this.capacity = capacity;
        this.starsNumber = starsNumber;
        this.roomStatus = roomStatus;
        this.details = details;
        this.currentGuestIdList.addAll(currentGuestIdList);
    }

    public Room() {
        super("", 0);
    }

    public void addGuest(long guestId) throws RuntimeException {
        currentGuestIdList.add(guestId);
        setRoomStatus(RoomStatus.BUSY);
    }

    public void removeGuest(long guestId) throws NoSuchElementException {
        if (!currentGuestIdList.remove(guestId)) {
            throw new NoSuchElementException("Such guest does not exist in that room");
        }
        if (getCurrentGuestIdList().isEmpty()) setRoomStatus(RoomStatus.FREE);
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
