package com.utkubayrak.jetbususerservice.service.impl;

import com.utkubayrak.jetbususerservice.model.User;
import com.utkubayrak.jetbususerservice.model.enums.RoleEnum;
import com.utkubayrak.jetbususerservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Trying to load user by email: {}", username);

        User user = userRepository.findByEmail(username);
        if (user == null) {
            logger.error("User not found with email: {}", username);
            throw new UsernameNotFoundException("User not found with email " + username);
        }

        RoleEnum role = user.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));

        logger.info("User found: {} with role: {}", user.getEmail(), role);

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
