package com.flightapp.controller;

import com.flightapp.dto.request.RegisterRequest;
import com.flightapp.dto.request.LoginRequest;
import com.flightapp.model.AppUser;
import com.flightapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/flight")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // REGISTER USER
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return "User registered successfully";
    }

    // LOGIN USER
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request, HttpSession session) {
        AppUser user = userService.login(request);
        session.setAttribute("userEmail", user.getEmail());
        return "Login successful";
    }
}
