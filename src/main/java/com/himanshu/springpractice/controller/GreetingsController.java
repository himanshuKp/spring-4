package com.himanshu.springpractice.controller;

import com.himanshu.springpractice.entity.Students;
import com.himanshu.springpractice.service.CustomUserDetailService;
import com.himanshu.springpractice.service.StudentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GreetingsController {

    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationManager authenticationManager;
    private final StudentsService studentsService;

    private static final Logger logger = LoggerFactory.getLogger(GreetingsController.class);

    public GreetingsController(CustomUserDetailService customUserDetailService, AuthenticationManager authenticationManager, StudentsService studentsService) {
        this.customUserDetailService = customUserDetailService;
        this.authenticationManager = authenticationManager;
        this.studentsService = studentsService;
    }

    @GetMapping("/greet")
    public String greetings(Model model) {
//        get authenticated user username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("Username from context: "+username);

        model.addAttribute("username", username);

        List<Students> students = studentsService.findAllStudents();
        logger.info("Students retrieved: {}", students);

        model.addAttribute("students", students);

        return "greet";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/registerUser")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password
    ) {
        try {
            customUserDetailService.registerUser(username, password);
        } catch (Exception usernameAlreadyExists) {
            return "/register?error";
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/login?success";
    }
}
