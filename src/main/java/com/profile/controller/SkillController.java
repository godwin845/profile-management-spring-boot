package com.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.profile.constant.HttpStatusCode;
import com.profile.service.SkillService;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<?> getSkills(@RequestAttribute("userId") Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(skillService.getSkills(userId));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to fetch skills: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveSkills(
            @RequestAttribute("userId") Long userId,
            @RequestBody List<String> skills) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body(skillService.saveOrUpdateSkills(userId, skills));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to save skills: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSkills(@RequestAttribute("userId") Long userId) {
        try {
            skillService.deleteSkills(userId);
            return ResponseEntity
                    .status(HttpStatusCode.OK.getCode())
                    .body("Skills deleted successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body("Failed to delete skills: " + e.getMessage());
        }
    }
}