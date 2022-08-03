package com.senla.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestCreationDTO {

    private String login;
    private String password;
    private String name;
    private String passport;
}
