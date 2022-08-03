package com.senla.security.basic;

import com.senla.dao.TokenDao;
import com.senla.model.Token;
import com.senla.security.basic.TokenAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenAuthProvider implements AuthenticationProvider {

    private final TokenDao tokenDao;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;

        Token tokenByValue;
        try {
             tokenByValue = tokenDao.getTokenByValue(tokenAuthentication.getName());
        } catch (Exception e) {
            log.error("Bad token", e);
            throw new IllegalArgumentException("Bad token");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenByValue.getGuest().getLogin());
        tokenAuthentication.setUserDetails(userDetails);
        tokenAuthentication.setAuthenticated(true);
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}