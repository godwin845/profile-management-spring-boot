package com.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.profile.constant.HttpStatusCode;
import com.profile.entity.Education;
import com.profile.service.EducationService;

@RestController
@RequestMapping("/api/education")
public class EducationController {

    private final EducationService service;

    public EducationController(EducationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestAttribute("userId") Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.getEducation(userId));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Server Error: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody Education request,
            @RequestAttribute("userId") Long userId) {

        try {
            request.setUserId(userId); // ✅ VERY IMPORTANT

            return ResponseEntity
                    .status(HttpStatusCode.CREATED.getCode())
                    .body(service.createEducation(request));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("already exists")) {
                return ResponseEntity
                        .status(HttpStatusCode.CONFLICT.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to create education");
        }
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestBody Education request,
            @RequestAttribute("userId") Long userId) {

        try {
            request.setUserId(userId); // ✅ VERY IMPORTANT

            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.updateEducation(request));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity
                        .status(HttpStatusCode.NOT_FOUND.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to update education");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestAttribute("userId") Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.deleteEducation(userId));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity
                        .status(HttpStatusCode.NOT_FOUND.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to delete education");
        }
    }
}