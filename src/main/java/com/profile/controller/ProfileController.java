package com.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.profile.constant.HttpStatusCode;
import com.profile.entity.Profile;
import com.profile.service.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(
            @ModelAttribute Profile request,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam(value = "resumeFile", required = false) MultipartFile resumeFile,
            @RequestAttribute("userId") Long userId
    ) {
        try {
            request.setUserId(userId); // ✅ CRITICAL

            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.createOrUpdateProfile(request, profileImage, resumeFile));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("PROFILE_ERROR: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestAttribute("userId") Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.getProfile(userId));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Server error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.deleteProfile(id));
        } catch (RuntimeException e) {
            if ("Profile not found".equals(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatusCode.NOT_FOUND.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Server error");
        }
    }
}