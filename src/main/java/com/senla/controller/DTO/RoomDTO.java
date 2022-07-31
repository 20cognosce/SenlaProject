package com.senla.controller.DTO;

import com.senla.model.Room;
import com.senla.model.RoomStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RoomDTO {

    private Long id;
    private String name;
    private Integer price;
    private Integer capacity;
    private Integer starsNumber;
    private List<GuestDTO> currentGuestsDtoList;
    private RoomStatus roomStatus;

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
