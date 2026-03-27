package com.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.profile.constant.HttpStatusCode;
import com.profile.entity.Certificate;
import com.profile.service.CertificateService;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestAttribute("userId") Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.getCertificate(userId));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Server Error: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> add(
            @RequestBody Certificate request,
            @RequestAttribute("userId") Long userId) {

        try {
            request.setUserId(userId); // ✅ VERY IMPORTANT

            return ResponseEntity
                    .status(HttpStatusCode.CREATED.getCode())
                    .body(service.addCertificate(request));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("already exists")) {
                return ResponseEntity
                        .status(HttpStatusCode.CONFLICT.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to add certificate");
        }
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestBody Certificate request,
            @RequestAttribute("userId") Long userId) {

        try {
            request.setUserId(userId); // ✅ VERY IMPORTANT

            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.updateCertificate(request));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity
                        .status(HttpStatusCode.NOT_FOUND.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to update certificate");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestAttribute("userId") Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(service.deleteCertificate(userId));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity
                        .status(HttpStatusCode.NOT_FOUND.getCode())
                        .body(e.getMessage());
            }
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to delete certificate");
        }
    }
}