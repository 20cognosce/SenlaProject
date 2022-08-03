package com.senla.controller;

import com.senla.controller.DTO.LoginDTO;
import com.senla.controller.DTO.TokenDTO;
import com.senla.dao.GuestDao;
import com.senla.model.Guest;
import com.senla.security.jwt.JwtTokenSupplier;
import com.senla.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class LoginController {

    @Value("${authorization.header}")
    private String authorizationHeader;
    private final LoginService loginService;
    private final JwtTokenSupplier jwtTokenSupplier;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        /*
        Спринг отказывался выполнять authenticate у AuthenticationManager, пришлось создать JwtAuthProvider
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getPassword()));
        */
        Guest guest = loginService.tryToLoginReturnGuestIfSuccess(loginDTO);
        String token = jwtTokenSupplier.generateToken(loginDTO.getLogin(), guest.getRole().name());
        return ResponseEntity.ok(new TokenDTO(loginDTO.getLogin(), token));

        //return ResponseEntity.ok(loginService.login(loginDTO)); //for basic variant
    }

    @PostMapping("/custom_logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = request.getHeader(authorizationHeader);
        loginService.logoutUserByToken(token);
        return ResponseEntity.ok("Logout successfully");
        /*
        /logout url занят спрингом, надо что-то настраивать, чтобы оно заработало, перемещу на другой url
         */
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        //Todo:
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}