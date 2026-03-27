package com.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.entity.Education;

import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Long> {

    Optional<Education> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
