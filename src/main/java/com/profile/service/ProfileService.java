package com.profile.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.profile.entity.Profile;
import com.profile.repository.ProfileRepository;

import java.io.File;
import java.io.IOException;

@Service
public class ProfileService {

    private final ProfileRepository repo;

    public ProfileService(ProfileRepository repo) {
        this.repo = repo;
    }

    public Profile createOrUpdateProfile(
            Profile request,
            MultipartFile profileImage,
            MultipartFile resumeFile
    ) throws IOException {

        Profile profile = repo.findByUserId(request.getUserId()).orElse(new Profile());

        profile.setUserId(request.getUserId());
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setEmail(request.getEmail());
        profile.setLocation(request.getLocation());
        profile.setBio(request.getBio());

        // Handle profile image
        if (profileImage != null && !profileImage.isEmpty()) {
            String path = saveFile(profileImage, "uploads/images/");
            profile.setProfileImage(path);
        }

        // Handle resume file
        if (resumeFile != null && !resumeFile.isEmpty()) {
            String path = saveFile(resumeFile, "uploads/resumes/");
            profile.setResumeFile(path);
        }

        return repo.save(profile);
    }

    public Profile getProfile(Long userId) {
        return repo.findByUserId(userId).orElse(null);
    }

    public String deleteProfile(Long id) {
        Profile profile = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        deleteFile(profile.getProfileImage());
        deleteFile(profile.getResumeFile());

        repo.delete(profile);
        return "Profile deleted successfully";
    }

    // ===== File Helpers =====

    private String saveFile(MultipartFile file, String dir) throws IOException {
        File folder = new File(dir);
        if (!folder.exists()) folder.mkdirs();

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String path = dir + filename;

        file.transferTo(new File(path));
        return path;
    }

    private void deleteFile(String path) {
        if (path == null) return;

        File file = new File(path);
        if (file.exists()) file.delete();
    }
}
