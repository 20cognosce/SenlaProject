package com.senla.controller.DTO;

import com.senla.model.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class RoomCreationDTO {

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
    private RoomStatus roomStatus;
}
