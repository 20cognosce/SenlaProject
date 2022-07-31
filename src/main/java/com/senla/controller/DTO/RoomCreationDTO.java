package com.senla.controller.DTO;

import com.senla.model.RoomStatus;
import lombok.Data;

@Data
public class RoomCreationDTO {

    private final String name;
    private final Integer price;
    private final Integer capacity;
    private final Integer starsNumber;
    private final RoomStatus roomStatus;
}
