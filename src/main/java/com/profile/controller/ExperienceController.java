package com.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.profile.constant.HttpStatusCode;
import com.profile.entity.Experience;
import com.profile.service.ExperienceService;

@RestController
@RequestMapping("/api/experience")
public class ExperienceController {

    private final ExperienceService service;

    public ExperienceController(ExperienceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestAttribute("userId") Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.getExperience(userId));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Server Error: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> add(
            @RequestBody Experience request,
            @RequestAttribute("userId") Long userId) {

        try {
            request.setUserId(userId); // ✅ CRITICAL

            return ResponseEntity
                    .status(HttpStatusCode.CREATED.getCode())
                    .body(service.addExperience(request));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("already exists")) {
                return ResponseEntity
                        .status(HttpStatusCode.CONFLICT.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to add experience");
        }
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestBody Experience request,
            @RequestAttribute("userId") Long userId) {

        try {
            request.setUserId(userId); // ✅ CRITICAL

            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.updateExperience(request));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity
                        .status(HttpStatusCode.NOT_FOUND.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to update experience");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestAttribute("userId") Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.deleteExperience(userId));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity
                        .status(HttpStatusCode.NOT_FOUND.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to delete experience");
        }
    }
}