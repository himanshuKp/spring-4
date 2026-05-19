package com.himanshu.springpractice.controller;

import com.himanshu.springpractice.service.CustomUserDetailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingsController {

    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationManager authenticationManager;

    public GreetingsController(CustomUserDetailService customUserDetailService, AuthenticationManager authenticationManager) {
        this.customUserDetailService = customUserDetailService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/greet")
    public String greetings(Model model) {
//        get authenticated user username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("Username from context: "+username);

        model.addAttribute("username", username);

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
