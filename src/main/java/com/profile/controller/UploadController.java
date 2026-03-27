package com.profile.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @PostMapping
    public String upload(
            @RequestParam("profileImage") MultipartFile profileImage,
            @RequestParam("resumeFile") MultipartFile resumeFile
    ) throws IOException {

        saveFile(profileImage, "uploads/images/");
        saveFile(resumeFile, "uploads/resumes/");

        return "Files uploaded successfully";
    }

    private void saveFile(MultipartFile file, String dir) throws IOException {
        File folder = new File(dir);
        if (!folder.exists()) folder.mkdirs();

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(dir + filename));
    }
}
