package com.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.profile.constant.HttpStatusCode;
import com.profile.entity.User;
import com.profile.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.CREATED.getCode())
                    .body(authService.register(user));
        } catch (RuntimeException e) {
            if ("User already exists".equals(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatusCode.BAD_REQUEST.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Server error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(authService.login(request.getEmail(), request.getPassword()));
        } catch (RuntimeException e) {
            if ("Invalid credentials".equals(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatusCode.UNAUTHORIZED.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Server error");
        }
    }
}