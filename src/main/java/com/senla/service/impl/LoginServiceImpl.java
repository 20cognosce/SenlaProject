package com.senla.service.impl;

import com.senla.controller.DTO.LoginDTO;
import com.senla.dao.GuestDao;
import com.senla.model.Guest;
import com.senla.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginServiceImpl implements LoginService {

    private final PasswordEncoder passwordEncoder;
    private final GuestDao guestDao;

    @Override
    public Guest tryToLoginReturnGuestIfSuccess(LoginDTO loginDTO) {
        try {
            Guest guest = guestDao.getGuestByLogin(loginDTO.getLogin());
            if (passwordEncoder.matches(loginDTO.getPassword(), guest.getHashPassword())) {
                return guest;
            } else {
                throw new IllegalArgumentException("Incorrect password");
            }
        } catch (Exception e) {
            log.error("Incorrect login or password", e);
            throw new IllegalArgumentException("Incorrect login or password");
        }
    }
}
