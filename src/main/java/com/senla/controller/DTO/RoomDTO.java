package com.senla.controller.DTO;

import com.senla.model.Room;
import com.senla.model.RoomStatus;
import lombok.Data;

import java.util.List;

@Data
public class RoomDTO {

    private final Long id;
    private final String name;
    private final int price;
    private final int capacity;
    private final int starsNumber;
    private final List<GuestDTO> currentGuestsDtoList;
    private final RoomStatus roomStatus;

    public RoomDTO(Room room, List<GuestDTO> currentGuestDtoList) {
        this.id = room.getId();
        this.name = room.getName();
        this.price = room.getPrice();
        this.capacity = room.getCapacity();
        this.starsNumber = room.getStarsNumber();
        this.roomStatus = room.getRoomStatus();
        this.currentGuestsDtoList = currentGuestDtoList;
    }
}
