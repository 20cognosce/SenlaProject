package com.senla.service;

import com.senla.controller.DTO.LoginDTO;
import com.senla.controller.DTO.TokenDTO;
import com.senla.model.Guest;

public interface LoginService {
    TokenDTO login(LoginDTO loginForm);
    void logoutUserByToken(String token);
    Guest tryToLoginReturnGuestIfSuccess(LoginDTO loginDTO);
}
