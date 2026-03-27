package com.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.entity.Experience;

import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    Optional<Experience> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
