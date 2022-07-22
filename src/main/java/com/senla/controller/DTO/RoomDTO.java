package com.senla.controller.DTO;

import com.senla.model.Room;
import com.senla.model.RoomStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
public class RoomDTO {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int price;
    @Getter
    @Setter
    private int capacity;
    @Getter
    @Setter
    private int starsNumber;
    @Getter
    @Setter
    private List<GuestDTO> currentGuestsDtoList;
    @Getter
    @Setter
    private RoomStatus roomStatus;

    public RoomDTO(Room room, List<GuestDTO> currentGuestDtoList) {
        this.name = room.getName();
        this.price = room.getPrice();
        this.capacity = room.getCapacity();
        this.starsNumber = room.getStarsNumber();
        this.roomStatus = room.getRoomStatus();
        this.currentGuestsDtoList = currentGuestDtoList;
    }
}
