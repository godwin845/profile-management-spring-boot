package com.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.profile.entity.Social;
import com.profile.entity.User;
import com.profile.repository.SocialRepository;
import com.profile.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SocialService {

    private final SocialRepository socialRepository;
    private final UserRepository userRepository;

    /** Get all social links for a user */
    public List<Social> getSocials(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return socialRepository.findByUser(user);
    }

    /** Create new social links (only if none exist) */
    @Transactional
    public List<Social> createSocials(Long userId, List<Social> socials) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!socialRepository.findByUser(user).isEmpty()) {
            throw new IllegalStateException("Social links already exist, use update instead");
        }

        socials.forEach(s -> s.setUser(user));
        return socialRepository.saveAll(socials);
    }

    /** Update existing social links */
    @Transactional
    public List<Social> updateSocials(Long userId, List<Social> socials) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        List<Social> existing = socialRepository.findByUser(user);
        if (existing.isEmpty()) {
            throw new NoSuchElementException("Social links not found, create first");
        }

        // delete old links and save new ones
        socialRepository.deleteByUser(user);
        socials.forEach(s -> s.setUser(user));
        return socialRepository.saveAll(socials);
    }

    /** Delete all social links for a user */
    @Transactional
    public void deleteSocials(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        List<Social> existing = socialRepository.findByUser(user);
        if (existing.isEmpty()) {
            throw new NoSuchElementException("Social links not found");
        }

        socialRepository.deleteByUser(user);
    }
}
