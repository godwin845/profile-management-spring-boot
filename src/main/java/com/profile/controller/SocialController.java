package com.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.profile.constant.HttpStatusCode;
import com.profile.entity.Social;
import com.profile.service.SocialService;

import java.util.List;

@RestController
@RequestMapping("/api/socials")
@RequiredArgsConstructor
public class SocialController {

    private final SocialService socialService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getSocials(@PathVariable Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(socialService.getSocials(userId));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to fetch socials: " + e.getMessage());
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> createSocials(@PathVariable Long userId, @RequestBody List<Social> socials) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.CREATED.getCode())
                    .body(socialService.createSocials(userId, socials));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to create socials: " + e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateSocials(@PathVariable Long userId, @RequestBody List<Social> socials) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(socialService.updateSocials(userId, socials));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to update socials: " + e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteSocials(@PathVariable Long userId) {
        try {
            socialService.deleteSocials(userId);
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body("Social links deleted successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to delete socials: " + e.getMessage());
        }
    }
}