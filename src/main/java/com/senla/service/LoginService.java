package com.senla.service;

import com.senla.controller.DTO.LoginDTO;
import com.senla.controller.DTO.TokenDTO;

public interface LoginService {
    TokenDTO login(LoginDTO loginForm);
}
