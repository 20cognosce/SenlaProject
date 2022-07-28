package com.senla.controller.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GuestCreationDTO {

    private final String name;
    private final String passport;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
}
