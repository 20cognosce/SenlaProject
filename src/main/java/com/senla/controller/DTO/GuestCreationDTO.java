package com.senla.controller.DTO;

import com.senla.model.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GuestCreationDTO {

    private final String login;
    private final String password;
    private final Role role;
    private final String name;
    private final String passport;
}
