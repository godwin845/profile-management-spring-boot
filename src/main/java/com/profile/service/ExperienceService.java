package com.profile.service;

import org.springframework.stereotype.Service;

import com.profile.entity.Experience;
import com.profile.repository.ExperienceRepository;

@Service
public class ExperienceService {

    private final ExperienceRepository repo;

    public ExperienceService(ExperienceRepository repo) {
        this.repo = repo;
    }

    public Experience getExperience(Long userId) {
        return repo.findByUserId(userId).orElse(null);
    }

    public Experience addExperience(Experience request) {
        repo.findByUserId(request.getUserId()).ifPresent(e -> {
            throw new RuntimeException("Experience already exists, use update instead");
        });

        return repo.save(request);
    }

    public Experience updateExperience(Experience request) {
        Experience exp = repo.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Experience not found, add first"));

        exp.setRole(request.getRole());
        exp.setCompany(request.getCompany());
        exp.setLocation(request.getLocation());
        exp.setDoj(request.getDoj());
        exp.setDoe(request.getDoe());
        exp.setPresent(request.getPresent());

        return repo.save(exp);
    }

    public String deleteExperience(Long userId) {
        Experience exp = repo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Experience not found"));

        repo.delete(exp);
        return "Experience deleted successfully";
    }
}
