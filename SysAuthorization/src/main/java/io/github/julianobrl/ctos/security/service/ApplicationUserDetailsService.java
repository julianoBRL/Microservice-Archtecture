package io.github.julianobrl.ctos.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.julianobrl.ctos.dtos.ApplicationUsers;
import io.github.julianobrl.ctos.services.UsersService;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersService usersService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new ApplicationUsers(usersService.getByUsrName(s).orElseThrow(() -> new UsernameNotFoundException("Username Not Found")));
    }
}