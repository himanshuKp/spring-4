package com.himanshu.springpractice.service;

import com.himanshu.springpractice.entity.User;
import com.himanshu.springpractice.entity.UserLog;
import com.himanshu.springpractice.repository.UserLogRepository;
import com.himanshu.springpractice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserLogRepository userLogRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailService.class);

    public CustomUserDetailService(UserRepository userRepository,
                                   UserLogRepository userLogRepository) {
        this.userRepository = userRepository;
        this.userLogRepository = userLogRepository;
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

    @Transactional
    public void registerUser(String username, String password) throws Exception {
        if (username == null){
            throw new IllegalArgumentException("Username is required");
        }
        boolean exists = userRepository.existsByUsernameIgnoreCase(username);
        System.out.println("exists = " + exists);
        if (exists) {
            throw new Exception("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword);
        User userSaved = null;

        try {
            userSaved = userRepository.save(user);
        } catch (DataIntegrityViolationException e){
            throw new Exception("Username already exists");
        }

        UserLog userLog = new UserLog();
        userLog.setUserId(userSaved.getId());
        userLog.setAction("User registered");
        userLog.setTimeStamp(LocalDateTime.now());

        userLogRepository.save(userLog);
    }
}
