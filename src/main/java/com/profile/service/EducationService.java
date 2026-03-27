package com.profile.service;

import org.springframework.stereotype.Service;

import com.profile.entity.Education;
import com.profile.repository.EducationRepository;

@Service
public class EducationService {

    private final EducationRepository repo;

    public EducationService(EducationRepository repo) {
        this.repo = repo;
    }

    public Education getEducation(Long userId) {
        return repo.findByUserId(userId).orElse(null);
    }

    public Education createEducation(Education request) {
        repo.findByUserId(request.getUserId()).ifPresent(e -> {
            throw new RuntimeException("Education already exists, use update instead");
        });

        return repo.save(request);
    }

    public Education updateEducation(Education request) {
        Education edu = repo.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Education not found, create first"));

        edu.setCollege(request.getCollege());
        edu.setDegree(request.getDegree());
        edu.setField(request.getField());
        edu.setLocation(request.getLocation());
        edu.setDoj(request.getDoj());
        edu.setDoe(request.getDoe());
        edu.setStudying(request.getStudying());

        return repo.save(edu);
    }

    public String deleteEducation(Long userId) {
        Education edu = repo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Education not found"));

        repo.delete(edu);
        return "Education deleted successfully";
    }
}
