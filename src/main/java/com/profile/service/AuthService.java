package com.profile.service;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.profile.entity.User;
import com.profile.repository.UserRepository;
import com.profile.utils.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public Object register(User request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new RuntimeException("User already exists");
        });

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(request);

        String token = jwtUtil.generateToken(request.getId());

        return Map.of(
                "token", token,
                "user", request
        );
    }

    public Object login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId());

        return Map.of(
                "token", token,
                "user", user
        );
    }
}
