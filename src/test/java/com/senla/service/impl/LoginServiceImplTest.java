package com.senla.service.impl;

import com.senla.controller.DTO.LoginDTO;
import com.senla.dao.GuestDao;
import com.senla.model.Guest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
    @Mock
    private GuestDao guestDao;
    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    void tryToLoginReturnGuestIfSuccess_IncorrectLogin_IllegalArgumentExceptionThrown() {
        LoginDTO loginDTO = new LoginDTO("user", "user");

        when(guestDao.getGuestByLogin("user")).thenThrow(new NoSuchElementException());

        assertThrows(IllegalArgumentException.class, () -> loginService.tryToLoginReturnGuestIfSuccess(loginDTO));
    }

    @Test
    void tryToLoginReturnGuestIfSuccess_IncorrectPassword_IllegalArgumentExceptionThrown() {
        LoginDTO loginDTO = new LoginDTO("user", "qwerty");
        Guest guest = new Guest();
        guest.setHashPassword(passwordEncoder.encode("user"));

        when(guestDao.getGuestByLogin("user")).thenReturn(guest);
        assertThrows(IllegalArgumentException.class, () -> loginService.tryToLoginReturnGuestIfSuccess(loginDTO));
    }

    @Test
    void tryToLoginReturnGuestIfSuccess_CorrectCredentials_GuestReturned() {
        LoginDTO loginDTO = new LoginDTO("user", "user");
        Guest guest = new Guest();
        guest.setHashPassword(passwordEncoder.encode("user"));

        when(guestDao.getGuestByLogin("user")).thenReturn(guest);
        doCallRealMethod().when(passwordEncoder).matches(anyString(), anyString());

        assertEquals(guest, loginService.tryToLoginReturnGuestIfSuccess(loginDTO));
    }
}