package com.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.profile.entity.Skill;
import com.profile.repository.SkillRepository;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public Skill getSkills(Long userId) {
        return skillRepository.findByUserId(userId)
                .orElse(null);
    }

    @Transactional
    public Skill saveOrUpdateSkills(Long userId, List<String> skills) {
        if (skills == null || skills.isEmpty()) {
            throw new IllegalArgumentException("Skills list cannot be empty");
        }

        List<String> normalized = skills.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .toList();

        Skill skill = skillRepository.findByUserId(userId)
                .orElse(new Skill());

        skill.setUserId(userId);
        skill.setSkills(normalized);

        return skillRepository.save(skill);
    }

    @Transactional
    public void deleteSkills(Long userId) {
        Skill skill = skillRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Skills not found"));

        skillRepository.delete(skill);
    }
}
