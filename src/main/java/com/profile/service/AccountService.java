package com.profile.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.profile.entity.DeleteRequest;
import com.profile.repository.CareerVisionRepository;
import com.profile.repository.CertificateRepository;
import com.profile.repository.DeleteRequestRepository;
import com.profile.repository.EducationRepository;
import com.profile.repository.ExperienceRepository;
import com.profile.repository.ProfileRepository;
import com.profile.repository.SkillRepository;
import com.profile.repository.SocialRepository;
import com.profile.repository.UserRepository;

@Service
public class AccountService {

    private final DeleteRequestRepository deleteRequestRepo;
    private final UserRepository userRepo;
    private final ProfileRepository profileRepo;
    private final SocialRepository socialRepo;
    private final SkillRepository skillRepo;
    private final EducationRepository educationRepo;
    private final ExperienceRepository experienceRepo;
    private final CertificateRepository certificateRepo;
    private final CareerVisionRepository careerVisionRepo;

    public AccountService(
        DeleteRequestRepository deleteRequestRepo,
        UserRepository userRepo,
        ProfileRepository profileRepo,
        SocialRepository socialRepo,
        SkillRepository skillRepo,
        EducationRepository educationRepo,
        ExperienceRepository experienceRepo,
        CertificateRepository certificateRepo,
        CareerVisionRepository careerVisionRepo
    ) {
        this.deleteRequestRepo = deleteRequestRepo;
        this.userRepo = userRepo;
        this.profileRepo = profileRepo;
        this.socialRepo = socialRepo;
        this.skillRepo = skillRepo;
        this.educationRepo = educationRepo;
        this.experienceRepo = experienceRepo;
        this.certificateRepo = certificateRepo;
        this.careerVisionRepo = careerVisionRepo;
    }

    @Transactional
    public String deleteAccount(Long userId, String reason) {
        if (reason == null || reason.trim().isEmpty()) {
            throw new RuntimeException("Reason is required");
        }

        String trimmedReason = reason.trim();

        // Save delete request
        DeleteRequest request = new DeleteRequest();
        request.setUserId(userId);
        request.setReason(trimmedReason);
        deleteRequestRepo.save(request);

        // Delete related data
        profileRepo.deleteById(userId);
        socialRepo.deleteById(userId);
        skillRepo.deleteByUserId(userId);
        educationRepo.deleteByUserId(userId);
        experienceRepo.deleteByUserId(userId);
        certificateRepo.deleteByUserId(userId);
        careerVisionRepo.deleteByUserId(userId);
        deleteRequestRepo.deleteByUserId(userId);

        // Delete user
        userRepo.deleteById(userId);

        return "Account and associated data deleted successfully";
    }
}
