package com.senla.javacourse.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@Entity
public class Room extends AbstractEntity {
    @Getter
    @Setter
    @Column(name="name")
    private String name;
    @Getter
    @Setter
    @Column(name="price")
    private int price;
    @Getter
    @Setter
    @Column(name="capacity")
    private int capacity;
    @Getter
    @Setter
    @Column(name="stars_number")
    private int starsNumber;
    @Getter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
    private final List<Guest> currentGuestList = new ArrayList<>();
    //TODO: failed to lazily initialize a collection of role: com.senla.javacourse.dao.entity.Room.currentGuestList, could not initialize proxy - no Session
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
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.starsNumber = starsNumber;
        this.roomStatus = roomStatus;
        this.details = "";
    }

    //total constructor
    public Room(String name, int price, int capacity, int starsNumber, RoomStatus roomStatus, String details,
                List<Guest> currentGuestList) {
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.starsNumber = starsNumber;
        this.roomStatus = roomStatus;
        this.details = details;
        this.currentGuestList.addAll(currentGuestList);
    }

    public Room() {

    }

    public void addGuest(Guest guest) throws RuntimeException {
        currentGuestList.add(guest);
        setRoomStatus(RoomStatus.BUSY);
    }

    public void removeGuest(Guest guest) throws NoSuchElementException {
        if (!currentGuestList.remove(guest)) {
            throw new NoSuchElementException("Such guest does not exist in that room");
        }
        if (getCurrentGuestList().isEmpty()) {
            setRoomStatus(RoomStatus.FREE);
        }
    }

    @JsonIgnore
    public boolean isUnavailableToSettle() {
        return (this.getRoomStatus() != RoomStatus.FREE && this.getRoomStatus() != RoomStatus.BUSY)
                || (getCurrentGuestList().size() >= getCapacity());
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
