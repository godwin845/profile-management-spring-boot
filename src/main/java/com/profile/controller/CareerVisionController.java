package com.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.profile.constant.HttpStatusCode;
import com.profile.entity.CareerVision;
import com.profile.service.CareerVisionService;

@RestController
@RequestMapping("/api/career-vision")
public class CareerVisionController {

    private final CareerVisionService service;

    public CareerVisionController(CareerVisionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestAttribute("userId") Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.getCareerVision(userId));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Server Error: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody CareerVision request,
            @RequestAttribute("userId") Long userId) {

        try {
            request.setUserId(userId); // ✅ IMPORTANT

            return ResponseEntity
                    .status(HttpStatusCode.CREATED.getCode())
                    .body(service.createCareerVision(request));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to create: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestBody CareerVision request,
            @RequestAttribute("userId") Long userId) {

        try {
            request.setUserId(userId); // ✅ IMPORTANT

            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.updateCareerVision(request));
        } catch (RuntimeException e) {
            if ("Career vision not found".equals(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatusCode.NOT_FOUND.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to update: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestAttribute("userId") Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.deleteCareerVision(userId));
        } catch (RuntimeException e) {
            if ("Career vision not found".equals(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatusCode.NOT_FOUND.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to delete: " + e.getMessage());
        }
    }
}