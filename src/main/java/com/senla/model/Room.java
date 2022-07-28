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
@Getter
@Setter
public class Room extends AbstractEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column(name = "capacity")
    private int capacity;
    @Column(name = "stars_number")
    private int starsNumber;
    @Column(name = "room_status")
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Guest> currentGuestList;
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
}
