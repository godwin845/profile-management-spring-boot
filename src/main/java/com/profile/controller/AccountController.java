package com.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.profile.entity.DeleteRequest;
import com.profile.service.AccountService;
import com.profile.constant.HttpStatusCode;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAccount(
            @RequestBody DeleteRequest request,
            @RequestAttribute("userId") Long userId
    ) {
        try {
            if (userId == null) {
                return ResponseEntity
                        .status(HttpStatusCode.UNAUTHORIZED.getCode())
                        .body("User not authenticated");
            }

            request.setUserId(userId); // ✅ ensure correct user

            String message = accountService.deleteAccount(userId, request.getReason());

            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(message);

        } catch (RuntimeException e) {
            if ("Reason is required".equals(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatusCode.BAD_REQUEST.getCode())
                        .body(e.getMessage());
            }

            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to process account deletion");
        }
    }
}