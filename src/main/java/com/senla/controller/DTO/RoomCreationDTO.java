package com.senla.controller.DTO;

import com.senla.model.RoomStatus;
import lombok.Data;

@Data
public class RoomCreationDTO {

    private final String name;
    private final int price;
    private final int capacity;
    private final int starsNumber;
    private final RoomStatus roomStatus;
}
