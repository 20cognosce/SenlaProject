package com.senla.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class GuestCreationDTO {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String passport;
    @Getter
    @Setter
    private LocalDate checkInDate;
    @Getter
    @Setter
    private LocalDate checkOutDate;
}
