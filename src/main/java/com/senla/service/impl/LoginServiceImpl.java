package com.senla.service.impl;

import com.senla.controller.DTO.LoginDTO;
import com.senla.controller.DTO.TokenDTO;
import com.senla.dao.GuestDao;
import com.senla.dao.TokenDao;
import com.senla.model.Guest;
import com.senla.model.Token;
import com.senla.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginServiceImpl implements LoginService {

    private final TokenDao tokenDao;
    private final PasswordEncoder passwordEncoder;
    private final GuestDao guestDao;

    @Transactional
    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        Guest guest;
        try {
            guest = guestDao.getGuestByLogin(loginDTO.getLogin());
        } catch (Exception e) {
            log.error("Guest not found", e);
            throw new IllegalArgumentException("Guest not found");
        }

        if (passwordEncoder.matches(loginDTO.getPassword(), guest.getHashPassword())) {
            Token token = Token.builder()
                    .guest(guest)
                    .value(RandomStringUtils.random(10, true, true))
                    .build();

            tokenDao.create(token);
            return TokenDTO.toTokenDTO(token);
        } else {
            throw new IllegalArgumentException("Incorrect password");
        }
    }

    @Transactional
    @Override
    public void logoutUserByToken(String token) {
        Guest guest = tokenDao.getTokenByValue(token).getGuest();
        guest.getTokens().forEach(tokenDao::delete);
    }

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
