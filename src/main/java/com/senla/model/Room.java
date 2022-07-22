package com.senla.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.NoSuchElementException;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Room extends AbstractEntity {

    @Getter
    @Setter
    @Column(name = "name")
    private String name;
    @Getter
    @Setter
    @Column(name = "price")
    private int price;
    @Getter
    @Setter
    @Column(name = "capacity")
    private int capacity;
    @Getter
    @Setter
    @Column(name = "stars_number")
    private int starsNumber;
    @Getter
    @Setter
    @Column(name = "room_status")
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Guest> currentGuestList;
    @Getter
    @Setter
    @Column(name = "details")
    private String details;

    //new Room constructor
    public Room(String name, int price, int capacity, int starsNumber, RoomStatus roomStatus) {
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.starsNumber = starsNumber;
        this.roomStatus = roomStatus;
        this.details = "";
    }

    public void addGuest(Guest guest) throws RuntimeException {
        currentGuestList.add(guest);
        setRoomStatus(RoomStatus.BUSY);
    }

    public void removeGuest(Guest guest) throws NoSuchElementException {
        if (!currentGuestList.remove(guest)) {
            throw new NoSuchElementException("Such guest does not exist in that room");
        }
        if (currentGuestList.isEmpty()) {
            setRoomStatus(RoomStatus.FREE);
        }
    }

    @JsonIgnore
    public boolean isUnavailableToSettle() {
        return (this.getRoomStatus() != RoomStatus.FREE && this.getRoomStatus() != RoomStatus.BUSY)
                || (currentGuestList.size() >= getCapacity());
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
