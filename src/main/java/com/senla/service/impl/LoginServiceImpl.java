package com.senla.service.impl;

import com.senla.controller.DTO.LoginDTO;
import com.senla.controller.DTO.TokenDTO;
import com.senla.dao.GuestDao;
import com.senla.model.Guest;
import com.senla.model.Token;
import com.senla.dao.TokenDao;
import com.senla.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final TokenDao tokenDao;
    private final PasswordEncoder passwordEncoder;
    private final GuestDao guestDao;

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        Optional<Guest> guestByLogin = guestDao.getGuestByLogin(loginDTO.getLogin());

        if (guestByLogin.isPresent()) {
            Guest guest = guestByLogin.get();

            if (passwordEncoder.matches(loginDTO.getPassword(), guest.getHashPassword())) {
                Token token = Token.builder()
                        .guest(guest)
                        .value(RandomStringUtils.random(10, true, true))
                        .build();

                tokenDao.create(token);
                return TokenDTO.toTokenDTO(token);
            }
        } throw new IllegalArgumentException("Guest not found");
    }
}
