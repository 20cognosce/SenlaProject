package com.senla.security;

import com.senla.dao.GuestDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final GuestDao guestDao;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new UserDetailsImpl(guestDao.getGuestByLogin(login));
    }
}
