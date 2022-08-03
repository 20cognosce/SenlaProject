package com.senla.service;

import com.senla.controller.DTO.LoginDTO;
import com.senla.model.Guest;

public interface LoginService {

    Guest tryToLoginReturnGuestIfSuccess(LoginDTO loginDTO);
}
