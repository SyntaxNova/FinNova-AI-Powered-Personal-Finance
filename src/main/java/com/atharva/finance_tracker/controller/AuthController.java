package com.atharva.finance_tracker.controller;

import com.atharva.finance_tracker.dto.RegisterRequest;
import com.atharva.finance_tracker.service.AuthService;
import org.springframework.web.bind.annotation.*;

import com.atharva.finance_tracker.dto.LoginRequest;
import com.atharva.finance_tracker.dto.AuthResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}