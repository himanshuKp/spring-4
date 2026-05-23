package com.himanshu.springpractice.service;

import com.himanshu.springpractice.entity.User;
import com.himanshu.springpractice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailService.class);

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        LOGGER.info("User found: {}", user.getUsername());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    public void registerUser(String username, String password) throws Exception {
        boolean findUsername = userRepository
                .findAll()
                .stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
        if (findUsername) {
            throw new Exception("Username already exists");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword);
        userRepository.save(user);
    }
}
